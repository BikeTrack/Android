package eu.biketrack.android.models.biketracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.util.concurrent.TimeUnit;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.models.data_send.SendBikeUpdate;
import eu.biketrack.android.session.LoginManagerModule;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 27/10/2017 for BikeTrack_Android.
 */

public class BikeTrackerNetwork implements BikeTrackerNetworkInterface {
    private static final String TAG = "BikeTrackerNetwork";
    BiketrackService biketrackService;
    LoginManagerModule loginManagerModule;
    BikeTrackerList bikeTrackerList;
    private Observable<User> userObservable;

    public BikeTrackerNetwork(LoginManagerModule loginManagerModule, BiketrackService biketrackService) {
        this.loginManagerModule = loginManagerModule;
        this.biketrackService = biketrackService;
        bikeTrackerList = BikeTrackerList.getInstance();
        bikeTrackerList.setBikeTrackerNetworkInterface(this);
    }

    @Override
    public LoginManagerModule getLoginManagerModule() {
        return loginManagerModule;
    }

    @Override
    public void updateBikeList() {
        getUserBikes(loginManagerModule.getUserId(), loginManagerModule.getToken());
    }

    @Override
    public void createBike(SendBikeInfo bike) {
        addBike(new SendBike(loginManagerModule.getUserId(), null, bike));
    }

    @Override
    public void updateBike(String bikeId, SendBikeInfo bike) {
        updateBike(new SendBikeUpdate(loginManagerModule.getUserId(), bikeId, bike));
    }

    @Override
    public void deleteBike(String bikeId, SendBikeInfo bike) {
        biketrackService.deleteBike(loginManagerModule.getToken(), new SendBike(loginManagerModule.getUserId(),
                bikeId, bike))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptAddBike>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.onError(e);
                    }

                    @Override
                    public void onNext(ReceptAddBike receptAddBike) {
                        bikeTrackerList.clear();
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.bikeCreated();
                    }
                });
    }

    private void updateBike(SendBikeUpdate bike) {
        biketrackService.updateBike(loginManagerModule.getToken(), bike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptAddBike>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.onError(e);
                    }

                    @Override
                    public void onNext(ReceptAddBike receptAddBike) {
                        Log.d(TAG, "onNext: " + receptAddBike.toString());
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.bikeCreated();
                    }
                });
    }

    private void addBike(SendBike bike) {
        biketrackService.addBike(loginManagerModule.getToken(), bike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptAddBike>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.onError(e);
                    }

                    @Override
                    public void onNext(ReceptAddBike receptAddBike) {
                        Log.d(TAG, "onNext: " + receptAddBike.toString());
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.bikeCreated();
                    }
                });
    }

    private Observable<ReceptUser> getUser(String userId, String token) {
        return biketrackService.getUser(token, userId);
    }

    @Override
    public Observable<ReceiveBike> getBike(String bike, String token) {
        return biketrackService.getBike(bike, token);
    }

    @Override
    public Observable<ReceiveTracker> getTracker(String tracker, String token) {
        return biketrackService.getTracker(tracker, token);
    }

    private void getUserBikes(String userId, String token) {
        Log.d(TAG, "getBikeArrayList: " + userId + " / " + token);
//        bikeTrackerList.clear();


        getUser(userId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptUser>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "getUserBikes onError: ", e);
                    }

                    @Override
                    public void onNext(ReceptUser receptUser) {
                        userObservable = Observable.just(receptUser.getUser());
                        Log.d(TAG, "onNext: " + receptUser.getUser());
                        getBikes(token);
                    }
                });
    }

    private void getBikes(String token) {
        Observable<String> bikeIdObservable = userObservable.flatMap(user -> {
            Log.d(TAG, "onNext: =>" + user);
            return Observable.from(user.getBikes());
        });
        bikeIdObservable.isEmpty().doOnNext(aBoolean -> {
            if (bikeTrackerList.listener != null && aBoolean) {
                bikeTrackerList.listener.listUpdated();
            }
        }).subscribe();
        bikeIdObservable.forEach(s -> {
            Log.d(TAG, "getBikeArrayList: " + s);
            getBike(s, token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<ReceiveBike>() {
                        @Override
                        public void onCompleted() {
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, "getBikes onError: ", e);
                        }

                        @Override
                        public void onNext(ReceiveBike receiveBike) {
                            Log.d(TAG, "onNext: " + receiveBike.getBike().toString());
                            bikeTrackerList.updateBike(receiveBike.getBike());
                            getBikePicture(receiveBike.getBike(), token);
                            getTrackerFromId(receiveBike.getBike(), token);
                        }
                    });
        });
    }

    private void getTrackerFromId(Bike bike, String token) {
        getTracker(bike.getTracker(), token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceiveTracker>() {
                    @Override
                    public void onCompleted() {
                        if (bikeTrackerList.listener != null) {
                            bikeTrackerList.listener.listUpdated();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.e(TAG, "getTrackerFromId onError: ", e);
                    }

                    @Override
                    public void onNext(ReceiveTracker receiveTracker) {
                        Log.d(TAG, "onNext: " + receiveTracker.getTracker().toString());
                        bikeTrackerList.updateTracker(receiveTracker.getTracker());
                    }
                });
    }

    private void getBikePicture(Bike bike, String token) {
        try {
            biketrackService.getBikePicture(token, bike.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(30, TimeUnit.SECONDS)
                    .doOnError(error -> {
                        Log.e(TAG, "getBikePicture onError: ", error);
                    })
                    .onErrorResumeNext(throwable -> {
                        return Observable.just(null);
                    })
                    .doOnNext(str -> {
                        Log.d(TAG, "getBikePicture: Image du vélo : " + str);
                        bikeTrackerList.updateBikePicture(bike.getId(), str);
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.bikeCreated();

                    })
                    .doOnCompleted(() -> {

                    })
                    .subscribe(s -> {
                            },
                            throwable -> {
                            },
                            () -> {
                            });
        } catch (Exception e) {

        }

    }

    @Override
    public void displayImage(String token, String id, ImageView imageView) {
        try {
            biketrackService.getBikePicture(token, id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(20, TimeUnit.SECONDS)
                    .doOnError(error -> {
                        Log.e(TAG, "displayImage onError: ", error);
                    })
                    .onErrorResumeNext(throwable -> {
                        return Observable.just(null);
                    })
                    .doOnNext(str -> {
                        if (str != null) {
                            try {
                                byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                imageView.setImageBitmap(decodedByte);
                            } catch (Exception e) {
                                Log.e(TAG, "onCreateView: ", e);
                            }
                        }
                    })
                    .doOnCompleted(() -> {

                    })
                    .subscribe(s -> {
                            },
                            throwable -> {
                            },
                            () -> {
                            });
        } catch (Exception e) {

        }
    }

    @Override
    public void uploadBikePicture(String picture, String bikeId) {
        Log.d(TAG, "uploadBikePicture: pict = " + picture + " // bikeId = " + bikeId);
        try {
            File tmp = new File(picture);
            Log.d(TAG, "uploadBikePicture: " + tmp);
            MultipartBody.Part filePart = MultipartBody.Part.
                    createFormData("photoBike", tmp.getName(), RequestBody.create(MediaType.parse("image/*"), tmp));
            biketrackService.uploadBikePicture(loginManagerModule.getToken(), bikeId, filePart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(30, TimeUnit.SECONDS)
                    .doOnError(err -> {
                        Log.d(TAG, "uploadBikePicture: Error !", err);
                    })
                    .retry(2)
                    .doOnError(error -> {
                        Log.e(TAG, "uploadBikePicture: ", error);
                    })
                    .onErrorResumeNext(throwable -> {
                        return Observable.just(null);
                    })
                    .doOnNext(receiveBike -> {
                        if (receiveBike != null)
                            bikeTrackerList.updateBike(receiveBike.getBike());
                        Log.d(TAG, "uploadBikePicture: upload fini");
                    })
                    .doOnCompleted(() -> {

                    })
                    .subscribe(s -> {
                            },
                            throwable -> {
                            },
                            () -> {
                            });
        } catch (Exception e) {
            Log.e(TAG, "uploadBikePicture: ", e);
        }

    }

    @Override
    public void uploadBikeBill(String picture, String bikeId) {
        Log.d(TAG, "uploadBikeBill: pict = " + picture + " // bikeId = " + bikeId);
        try {
            File tmp = new File(picture);
            Log.d(TAG, "uploadBikeBill: " + tmp);
            MultipartBody.Part filePart = MultipartBody.Part.
                    createFormData("photoBill", tmp.getName(), RequestBody.create(MediaType.parse("image/*"), tmp));
            biketrackService.uploadBikeBill(loginManagerModule.getToken(), bikeId, filePart)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(30, TimeUnit.SECONDS)
                    .doOnError(err -> {
                        Log.d(TAG, "uploadBikeBill: Error !", err);
                    })
                    .retry(2)
                    .doOnError(error -> {
                        Log.e(TAG, "onError: uploadBikeBill ", error);
                    })
                    .onErrorResumeNext(throwable -> {
                        return Observable.just(null);
                    })
                    .doOnNext(receiveBike -> {
                        if (receiveBike != null)
                            bikeTrackerList.updateBike(receiveBike.getBike());
                        Log.d(TAG, "uploadBikeBill: upload fini");
                    })
                    .doOnCompleted(() -> {

                    })
                    .subscribe(s -> {
                            },
                            throwable -> {
                            },
                            () -> {
                            });
        } catch (Exception e) {
            Log.e(TAG, "uploadBikeBill: ", e);
        }
    }

    @Override
    public void downloadBikeBill(String bikeId) {
        try {
            biketrackService.getBikeBill(loginManagerModule.getToken(), bikeId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .timeout(20, TimeUnit.SECONDS)
                    .doOnError(error -> {
                        Log.e(TAG, "downloadBikeBill onError: ", error);
                    })
                    .onErrorResumeNext(throwable -> {
                        return Observable.just(null);
                    })
                    .doOnNext(str -> {
                        if (bikeTrackerList.listener != null) {
                            if (str != null && !"".equals(str)) {
                                byte[] decodedString = Base64.decode(str, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                bikeTrackerList.listener.updatePicture(decodedByte);
                            } else bikeTrackerList.listener.updatePicture(null);
                        }

                    })
                    .doOnCompleted(() -> {

                    })
                    .subscribe(s -> {
                            },
                            throwable -> {
                                Log.e(TAG, "downloadBikeBill: ", throwable);
                                bikeTrackerList.listener.updatePicture(null);
                            },
                            () -> {
                            });
        } catch (Exception e) {

        }
    }
}
