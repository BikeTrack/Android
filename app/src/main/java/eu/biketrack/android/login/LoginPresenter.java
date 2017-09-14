package eu.biketrack.android.login;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginPresenter implements LoginMVP.Presenter {
    private LoginMVP.View view;
    private LoginMVP.Model model;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
    }
    private static final String TAG = "LoginPresenter";

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void connexionButtonClicked() {
        model.connection(view.getUserEmail(), view.getUserPassword());

    }
}
