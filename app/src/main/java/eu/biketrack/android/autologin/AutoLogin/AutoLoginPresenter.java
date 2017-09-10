package eu.biketrack.android.autologin.AutoLogin;

import android.util.Log;

import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginPresenter implements AutoLoginMVP.Presenter {
    private AutoLoginMVP.View view;
    private AutoLoginMVP.Model model;
    private Session session;
    private LoginManagerModule loginManagerModule;
    public AutoLoginPresenter(AutoLoginMVP.Model model) {
        this.model = model;
    }
    private static final String TAG = "AutoLoginPresenter";

    @Override
    public void setView(AutoLoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setLoginManager(LoginManagerModule loginManagerModule) {
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void tryConnection() {
        Log.d(TAG, "tryConnection: start");
        view.displayProgressBar(true);
        if (loginManagerModule.getToken() != null && loginManagerModule.getUserId() != null){
            Log.d(TAG, "tryConnection: token is not null and userid is not null");
            model.getUserFromNetwork(loginManagerModule.getUserId(), loginManagerModule.getToken());
        } else {
            Log.d(TAG, "tryConnection: ask for login");
            view.openLogin();
        }
        view.displayProgressBar(false);
        Log.d(TAG, "tryConnection: stop");
    }
}
