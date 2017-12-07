package eu.biketrack.android.subscription;

import android.util.Log;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

public class SubscriptionModel implements SubscriptionMVP.Model {
    private static final String TAG = "SubscriptionModel";
    private SubscriptionNetworkInterface subscriptionNetworkInterface;
    private LoginManagerModule loginManagerModule;
    private SubscriptionMVP.Presenter presenter;
    private Throwable error;
    private rx.Subscription s;
    private rx.Subscription s1;
    private boolean loginDone = false;

    public SubscriptionModel(SubscriptionNetworkInterface subscriptionNetworkInterface, LoginManagerModule loginManagerModule) {
        this.subscriptionNetworkInterface = subscriptionNetworkInterface;
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void setPresenter(SubscriptionMVP.Presenter presenter) {
        this.presenter = presenter;
    }


    @Override
    public void subscription(String email, String password) {
        AuthUser authUser = new AuthUser(email, password);
        loginDone = false;
        Observable<SignupReception> signupReceptionObservable = subscriptionNetworkInterface.signup(authUser);
        Subscriber<SignupReception> signupReceptionSubscriber = new Subscriber<SignupReception>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                error = e;
                this.unsubscribe();
            }

            @Override
            public void onNext(SignupReception signupReception) {
                if (signupReception.getSuccess()) {
                    connection(authUser);
                }
                error = null;
            }
        };
        s = signupReceptionObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(signupReceptionSubscriber);
    }

    private void connection(AuthUser authUser){
        Observable<AuthenticateReception> authenticateReceptionObservable = subscriptionNetworkInterface.connection(authUser);
        Subscriber<AuthenticateReception> authenticateReceptionSubscriber = new Subscriber<AuthenticateReception>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "connection: Completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                error = e;
                this.unsubscribe();
            }

            @Override
            public void onNext(AuthenticateReception authenticateReception) {
                Log.d(TAG, "connection: " + authenticateReception);
                loginManagerModule.storeEmail(authUser.getEmail());
                loginManagerModule.storeToken(authenticateReception.getToken());
                loginManagerModule.storeUserId(authenticateReception.getUserId());
                error = null;
                loginDone = true;
                destroyIt();
            }
        };
        s1 = authenticateReceptionObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(authenticateReceptionSubscriber);
    }

    @Override
    public Throwable getError() {
        return error;
    }

    private void destroyIt(){
        if (!s.isUnsubscribed())
            s.unsubscribe();
        if (!s1.isUnsubscribed())
            s1.unsubscribe();
        if (error == null)
            presenter.viewAfterSubscription(loginDone);
    }
}