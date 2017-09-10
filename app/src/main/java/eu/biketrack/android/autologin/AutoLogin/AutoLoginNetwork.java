package eu.biketrack.android.autologin.AutoLogin;

import javax.inject.Inject;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginNetwork implements AutoLoginNetworkInterface{
    private static final String TAG = "AutoLoginNetwork";

    private BiketrackService biketrackService;

    @Inject
    public AutoLoginNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public Observable<User> getUser(String userId, String token) {

        Observable<ReceptUser> receptUserObservable = biketrackService.getUser(token, userId);
        return receptUserObservable.flatMap(new Func1<ReceptUser, Observable<User>>() {
            @Override
            public Observable<User> call(ReceptUser receptUser) {
                return Observable.just(receptUser.getUser());
            }
        });
    }
}
