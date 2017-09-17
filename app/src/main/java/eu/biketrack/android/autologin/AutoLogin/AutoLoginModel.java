package eu.biketrack.android.autologin.AutoLogin;

import android.util.Log;

import javax.inject.Inject;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.session.Session;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginModel implements AutoLoginMVP.Model{
    private static final String TAG = "AutoLoginModel";
    private AutoLoginNetworkInterface autoLoginNetworkInterface;
    private Session session;
    private Throwable error = null;
    Subscription s;
    private AutoLoginMVP.Presenter presenter;

    public AutoLoginModel(AutoLoginNetworkInterface autoLoginNetworkInterface, Session session) {
        this.autoLoginNetworkInterface = autoLoginNetworkInterface;
        this.session = session;
    }

    @Override
    public void setPresenter(AutoLoginMVP.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void fillSession(String userId, String token) {
        session.setToken(token);
        session.setUserId(userId);
        Log.d(TAG, "fillSession: filled");
    }

    @Override
    public void getUserFromNetwork(String userId, String token) {
        Log.d(TAG, "getUserFromNetwork");
        Subscriber<ReceptUser> receptUserSubscriber = new Subscriber<ReceptUser>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
                error = e;
                destroyIt();
            }

            @Override
            public void onNext(ReceptUser receptUser) {
                fillSession(userId, token);
                destroyIt();
            }
        };

        s = autoLoginNetworkInterface.getUser(userId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(receptUserSubscriber);

    }

    @Override
    public Throwable getError() {
        return error;
    }

    private void destroyIt(){
        if (!s.isUnsubscribed())
            s.unsubscribe();
        presenter.viewAfterGettingUser();
    }
}
