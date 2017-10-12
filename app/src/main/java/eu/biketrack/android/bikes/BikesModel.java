package eu.biketrack.android.bikes;

import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public class BikesModel implements BikesMVP.Model {
    private static final String TAG = "BikesModel";
    private BikesMVP.Presenter presenter;
    private BikesNetworkInterface bikesNetworkInterface;
    private LoginManagerModule loginManagerModule;
    private Throwable error = null;

    public BikesModel(BikesNetworkInterface bikesNetworkInterface, LoginManagerModule loginManagerModule) {
        this.bikesNetworkInterface = bikesNetworkInterface;
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void setPresenter(BikesMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getBikes(){
        bikesNetworkInterface.getBikeArrayList(loginManagerModule.getUserId(), loginManagerModule.getToken());
    }

    @Override
    public Throwable getError() {
        return null;
    }
}
