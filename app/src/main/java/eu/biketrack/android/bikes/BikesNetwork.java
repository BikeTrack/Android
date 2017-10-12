package eu.biketrack.android.bikes;

import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.Tracker;
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
    private ArrayList<Pair<Bike, Tracker>> tmp = new ArrayList<>();

    public BikesNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
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
/*
    presenter call updateList
    quand la liste est updated, le model envoie les données au presenter.

 */
    @Override
    public ArrayList<Pair<Bike, Tracker>> getBikeArrayList(String userId, String token) {


        return tmp;
    }

    public void updateList(String userId, String token){
        Log.d(TAG, "getBikeArrayList: " + userId + " / " + token);
        tmp.clear();


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
                        tmp.add(new Pair<>(bike, receiveTracker.getTracker()));
                    }
                });
    }
}
