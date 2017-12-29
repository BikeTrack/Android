package eu.biketrack.android.autologin;


import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public interface AutoLoginNetworkInterface {

    Observable<AuthenticateReception> connect(AuthUser authUsers);
}
