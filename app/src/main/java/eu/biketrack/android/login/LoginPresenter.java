package eu.biketrack.android.login;

import android.util.Log;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginPresenter implements LoginMVP.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginMVP.View view;
    private LoginMVP.Model model;
    private boolean connectionByFacebook = false;
    private String fbemail;
    private String fbtoken;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void connexionButtonClicked() {
        model.connection(view.getUserEmail(), view.getUserPassword(), view.getRememberMe());
        view.loading(true);
    }

    @Override
    public void viewAfterConnection() {
        Log.d(TAG, "viewAfterConnection: ");
        if (model.getError() == null)
            view.close();
        else {
            if (model.getError().getMessage().equals("HTTP 401 Unauthorized") && connectionByFacebook) {
                model.subscriptionByFacebook(fbemail, fbtoken);
                connectionByFacebook = false;
            }
            view.loading(false);
        }
    }

    @Override
    public void goToSubscribe() {
        view.openSubscribe();
    }

    @Override
    public void facebookClicked(String email, String token) {
        connectionByFacebook = true;
        model.connection(email, token, true);
        fbemail = email;
        fbtoken = token;
    }
}
