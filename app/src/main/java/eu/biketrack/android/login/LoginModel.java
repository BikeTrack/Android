package eu.biketrack.android.login;

import android.util.Log;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginModel implements LoginMVP.Model {
    private static final String TAG = "LoginModel";
    private BiketrackService biketrackService;
    private LoginNetworkInterface loginNetworkInterface;
    private LoginManagerModule loginManagerModule;
    private Throwable error = null;
    private boolean completed = false;
    private LoginMVP.Presenter presenter;
    private Subscription s;

    public LoginModel(LoginNetworkInterface loginNetworkInterface, LoginManagerModule loginManagerModule) {
        this.loginNetworkInterface = loginNetworkInterface;
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void setPresenter(LoginMVP.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void setBiketrackService(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public void connection(String email, String password) {
        AuthUser authUser = new AuthUser(email, password);

        Observable<AuthenticateReception> authenticateReceptionObservable = loginNetworkInterface.connection(authUser);
        Subscriber<AuthenticateReception> authenticateReceptionSubscriber = new Subscriber<AuthenticateReception>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "connection: Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                error = e;
                completed = true;
                this.unsubscribe();
            }

            @Override
            public void onNext(AuthenticateReception authenticateReception) {
                Log.d(TAG, "connection: " + authenticateReception);
                loginManagerModule.storeEmail(email);
                loginManagerModule.storeToken(authenticateReception.getToken());
                loginManagerModule.storeUserId(authenticateReception.getUserId());
                error = null;
                completed = true;
                destroyIt();
            }
        };
         s = authenticateReceptionObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(authenticateReceptionSubscriber);
    }

    @Override
    public Throwable getError() {
        return error;
    }

    private void destroyIt(){
        if (!s.isUnsubscribed())
            s.unsubscribe();
        if (error == null)
            presenter.viewAfterConnection();
    }

}
