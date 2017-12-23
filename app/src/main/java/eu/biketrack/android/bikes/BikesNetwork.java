package eu.biketrack.android.bikes;

import android.util.Log;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public class BikesNetwork implements BikesNetworkInterface{
    private static final String TAG = "BikesNetwork";
    private BiketrackService biketrackService;
    private Observable<User> userObservable;
    private BikeTrackerList bikeTrackerList;
    private BikesMVP.Model model;

    @Override
    public void setModel(BikesMVP.Model model) {
        this.model = model;
    }

    public BikesNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
        bikeTrackerList = BikeTrackerList.getInstance();
    }

    @Override
    public Observable<ReceptUser> getUser(String userId, String token) {
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

    @Override
    public void getBikeArrayList(String userId, String token) {
        updateList(userId, token);
    }

    private void updateList(String userId, String token){
        Log.d(TAG, "getBikeArrayList: " + userId + " / " + token);
//        bikeTrackerList.clear();


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
                        model.updateDone();
                    }
                });
    }
}
