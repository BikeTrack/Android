package eu.biketrack.android.login;

import android.util.Log;

import javax.inject.Inject;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginModel implements LoginMVP.Model {
    private static final String TAG = "LoginModel";

    @Inject
    public BiketrackService biketrackService;

    @Inject
    public LoginManagerModule loginManagerModule;

    @Override
    public void connection(String email, String password) {
        AuthUser authUser = new AuthUser(email, password);
        LoginNetworkInterface loginNetworkInterface = new LoginNetwork(biketrackService);
        Observable<AuthenticateReception> authenticateReceptionObservable = loginNetworkInterface.connection(authUser);
        authenticateReceptionObservable.doOnNext(authenticateReception -> {
            loginManagerModule.storeEmail(email);
            loginManagerModule.storeUserId(authenticateReception.getUserId());
            loginManagerModule.storeToken(authenticateReception.getToken());});
        authenticateReceptionObservable.doOnError(throwable -> Log.e(TAG, "connection: error ", throwable));
    }
}
