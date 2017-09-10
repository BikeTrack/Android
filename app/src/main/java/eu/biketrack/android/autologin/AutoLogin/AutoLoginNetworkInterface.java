package eu.biketrack.android.autologin.AutoLogin;


import eu.biketrack.android.models.User;
import rx.Observable;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public interface AutoLoginNetworkInterface {

    Observable<User> getUser(String userId, String token);
}
