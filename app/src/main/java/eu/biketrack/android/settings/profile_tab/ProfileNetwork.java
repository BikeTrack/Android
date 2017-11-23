package eu.biketrack.android.settings.profile_tab;

import android.util.Log;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 20/11/2017 for BikeTrack_Android.
 */

public class ProfileNetwork implements ProfileNetworkInterface {
    private static final String TAG = "ProfileNetwork";
    private BiketrackService biketrackService;

    public ProfileNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public  Observable<ReceptUser> getUser(String userId, String token) {
        return biketrackService.getUser(token, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.e(TAG, "getUser: ", error);
                });
//                .subscribe(new Subscriber<ReceptUser>() {
//                    @Override
//                    public void onCompleted(){
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e(TAG, "onError: ", e);
//                    }
//
//                    @Override
//                    public void onNext(ReceptUser receptUser) {
//                        Log.d(TAG, "onNext: " + receptUser.getUser());
//                        userObservable = Observable.just(receptUser.getUser());
//                    }
//                });
    }

}
