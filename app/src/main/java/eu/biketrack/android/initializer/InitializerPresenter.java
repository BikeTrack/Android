package eu.biketrack.android.initializer;

import android.content.Context;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class InitializerPresenter implements InitializerMVP.Presenter {
    private static final String TAG = "InitializerPresenter";
    private InitializerMVP.View view;
    private InitializerMVP.Model model;

    public InitializerPresenter(InitializerMVP.Model model) {
        this.model = model;
    }

    @Override
    public void initApplication(Context context) {
        model.initLoginManager(context);
        model.language(context);
        view.openAutoLoginFragment();
    }

    @Override
    public void setView(InitializerMVP.View view) {
        this.view = view;
    }
}
