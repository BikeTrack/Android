package eu.biketrack.android.autologin.al_activity;

import android.content.Context;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginActPresenter implements AutoLoginActMVP.Presenter {
    private static final String TAG = "AutoLoginActPresenter";
    private AutoLoginActMVP.View view;
    private AutoLoginActMVP.Model model;

    public AutoLoginActPresenter(AutoLoginActMVP.Model model) {
        this.model = model;
    }

    @Override
    public void initApplication(Context context) {
        model.initLoginManager(context);
        model.language(context);
        view.openAutoLoginFragment();
    }

    @Override
    public void setView(AutoLoginActMVP.View view) {
        this.view = view;
    }
}
