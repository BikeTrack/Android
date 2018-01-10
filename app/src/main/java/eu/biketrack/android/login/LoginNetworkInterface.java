package eu.biketrack.android.login;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public interface LoginNetworkInterface {
    Observable<AuthenticateReception> connection(AuthUser authUser);

    Observable<SignupReception> signupFb(AuthUser authUser);

}
