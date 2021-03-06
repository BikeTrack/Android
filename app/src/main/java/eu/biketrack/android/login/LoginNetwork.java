package eu.biketrack.android.login;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginNetwork implements LoginNetworkInterface{
    private static final String TAG = "LoginNetwork";
    private BiketrackService biketrackService;

    public LoginNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public Observable<AuthenticateReception> connection(AuthUser authUser) {
        Observable<AuthenticateReception> authenticateReceptionObservable =  biketrackService.connectUser(authUser);
        return authenticateReceptionObservable;
    }
}
