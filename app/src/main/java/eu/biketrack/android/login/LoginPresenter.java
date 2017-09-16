package eu.biketrack.android.login;

import eu.biketrack.android.api_connection.BiketrackService;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginPresenter implements LoginMVP.Presenter {
    private LoginMVP.View view;
    private LoginMVP.Model model;
    private BiketrackService biketrackService;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
    }
    private static final String TAG = "LoginPresenter";

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void setBiketrackService(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public void connexionButtonClicked() {
        model.connection(view.getUserEmail(), view.getUserPassword());
        if (model.getError() == null)
            view.close();
    }
}
