package eu.biketrack.android.subscription;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

public interface SubscriptionNetworkInterface {
    Observable<SignupReception> signup(AuthUser authUser);
    Observable<AuthenticateReception> connection(AuthUser authUser);
}
