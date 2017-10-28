package eu.biketrack.android.models.biketracker;

import android.util.Log;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 27/10/2017 for BikeTrack_Android.
 */

public class BikeTrackerNetwork implements BikeTrackerNetworkInterface{
    private static final String TAG = "BikeTrackerNetwork";
    BiketrackService biketrackService;
    LoginManagerModule loginManagerModule;
    BikeTrackerList bikeTrackerList;
    private Observable<User> userObservable;

    public BikeTrackerNetwork(LoginManagerModule loginManagerModule, BiketrackService biketrackService) {
        this.loginManagerModule = loginManagerModule;
        this.biketrackService = biketrackService;
        bikeTrackerList = BikeTrackerList.getInstance();
    }

    @Override
    public void updateBike() {
        getUserBikes(loginManagerModule.getUserId(), loginManagerModule.getToken());
    }

    @Override
    public void createBike(SendBikeInfo bike) {
        addBike(new SendBike(loginManagerModule.getUserId(), null, bike));
    }

    private void addBike(SendBike bike){
        biketrackService.addBike(loginManagerModule.getToken(), bike)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptAddBike>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

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

    private Observable<ReceiveBike> getBike(String bike, String token) {
        return biketrackService.getBike(bike, token);
    }

    private Observable<ReceiveTracker> getTracker(String tracker, String token) {
        return biketrackService.getTracker(tracker, token);
    }

    private void getUserBikes(String userId, String token){
        Log.d(TAG, "getBikeArrayList: " + userId + " / " + token);
        bikeTrackerList.clear();


        getUser(userId ,token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceptUser>() {
                    @Override
                    public void onCompleted(){
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(ReceptUser receptUser) {
                        userObservable = userObservable.just(receptUser.getUser());
                        Log.d(TAG, "onNext: " + receptUser.getUser());
                        getBikes(token);
                    }
                });
    }

    private void getBikes(String token){
        Observable<String> bikeIdObservable = userObservable.flatMap(user -> {
            Log.d(TAG, "onNext: =>" + user);
            return Observable.from(user.getBikes());
        });
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
                            Log.e(TAG, "onError: ", e);
                        }

                        @Override
                        public void onNext(ReceiveBike receiveBike) {
                            Log.d(TAG, "onNext: " + receiveBike.getBike().toString());
                            getTrackerFromId(receiveBike.getBike(), token);
                        }
                    });
        });
    }

    private void getTrackerFromId(Bike bike, String token){
        getTracker(bike.getTracker(), token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ReceiveTracker>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(ReceiveTracker receiveTracker) {
                        Log.d(TAG, "onNext: " + receiveTracker.getTracker().toString());
                        bikeTrackerList.addPair(bike, receiveTracker.getTracker());
                        if (bikeTrackerList.listener != null)
                            bikeTrackerList.listener.listUpdated();
                    }
                });
    }
}
