package eu.biketrack.android.autologin.AutoLogin;

import android.util.Log;

import javax.inject.Inject;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import rx.Observable;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginModel implements AutoLoginMVP.Model{
    private static final String TAG = "AutoLoginModel";

    @Inject
    public BiketrackService biketrackService;

    @Override
    public void fillSession(User user) {
        Log.d(TAG, "fillSession: user = " + user);
    }

    @Override
    public void getUserFromNetwork(String userid, String token) {
        AutoLoginNetworkInterface autoLoginNetworkInterface = new AutoLoginNetwork(biketrackService);
        Log.d(TAG, "getUserFromNetwork");
        Observable<User> user_obs = autoLoginNetworkInterface.getUser(userid, token);
        user_obs.doOnNext(user -> fillSession(user));
    }
}
