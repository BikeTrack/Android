package eu.biketrack.android.login;

import android.util.Log;

import javax.inject.Inject;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginNetwork implements LoginNetworkInterface{
    private static final String TAG = "LoginNetwork";
    private BiketrackService biketrackService;

    @Inject
    public LoginNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public Observable<AuthenticateReception> connection(AuthUser authUser) {
        Log.d(TAG, "connection: biketrackservice = " + biketrackService);
        Observable<AuthenticateReception> receptAuthenticateReceptionObservable = biketrackService.connectUser(authUser);
        return receptAuthenticateReceptionObservable;
    }
}
