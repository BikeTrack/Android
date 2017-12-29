package eu.biketrack.android.autologin;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginModel implements AutoLoginMVP.Model {
    private static final String TAG = "AutoLoginModel";
    Subscription s;
    private AutoLoginNetworkInterface autoLoginNetworkInterface;
    private LoginManagerModule loginManagerModule;
    private Throwable error = null;
    private AutoLoginMVP.Presenter presenter;

    public AutoLoginModel(AutoLoginNetworkInterface autoLoginNetworkInterface, LoginManagerModule loginManagerModule) {
        this.autoLoginNetworkInterface = autoLoginNetworkInterface;
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void setPresenter(AutoLoginMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void fillSession(String userId, String token) {
        if (userId.equals(loginManagerModule.getUserId())) {
            loginManagerModule.storeToken(token);
        } else {
            error = new Throwable();
        }
        if (!loginManagerModule.getRememberMe())
            loginManagerModule.storePassword(null);
    }

    @Override
    public void connect() {
        Log.d(TAG, "getUserFromNetwork");

        AuthUser authUser = new AuthUser(loginManagerModule.getEmail(), loginManagerModule.getPassword());
        s = autoLoginNetworkInterface.connect(authUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(5, TimeUnit.SECONDS)
                .doOnError(err -> {
                    Log.d(TAG, "connection: Error !", err);
                })
                .retry(2)
                .doOnError(err -> {
                    Log.d(TAG, "connection: Error !", err);
                    error = err;
                    destroyIt();
                })
                .subscribe(new Subscriber<AuthenticateReception>() {
                    @Override
                    public void onCompleted() {
                        destroyIt();
                    }

                    @Override
                    public void onError(Throwable e) {
                        error = e;
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(AuthenticateReception authenticateReception) {
                        fillSession(authenticateReception.getUserId(), authenticateReception.getToken());
                    }
                });

    }

    @Override
    public Throwable getError() {
        return error;
    }

    private void destroyIt() {
        if (!s.isUnsubscribed())
            s.unsubscribe();
        presenter.viewAfterGettingUser();
    }
}
