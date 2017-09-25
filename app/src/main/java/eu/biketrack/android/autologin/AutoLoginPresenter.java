package eu.biketrack.android.autologin;

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
        this.model.setPresenter(this);
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
        view.displayProgressBar(true);
        if (loginManagerModule.getToken() != null && loginManagerModule.getUserId() != null){
            model.getUserFromNetwork(loginManagerModule.getUserId(), loginManagerModule.getToken());
        } else {
            view.displayProgressBar(false);
            view.openLogin();
        }
        view.displayProgressBar(false);
    }

    @Override
    public void viewAfterGettingUser(){
        view.displayProgressBar(true);
        if (model.getError() == null) {
            view.displayProgressBar(false);
            view.openBikes();
        } else {
            loginManagerModule.clear();
            view.displayProgressBar(false);
            view.openLogin();
        }
    }
}
