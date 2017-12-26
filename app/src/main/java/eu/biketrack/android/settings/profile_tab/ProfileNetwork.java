package eu.biketrack.android.settings.profile_tab;

import android.util.Log;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_send.DeleteUser;
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
    }

    @Override
    public Observable<ReceptDeleteUser> deleteUser(String userId, String token) {
        return biketrackService.deleteUser(token, new DeleteUser(userId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    Log.e(TAG, "deleteUser: ", error);
                });
    }
}
