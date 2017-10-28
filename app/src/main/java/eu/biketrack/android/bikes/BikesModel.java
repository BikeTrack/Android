package eu.biketrack.android.bikes;

import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
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
    private BikeTrackerNetworkInterface bikeTrackerNetworkInterface;

    public BikesModel(BikesNetworkInterface bikesNetworkInterface, LoginManagerModule loginManagerModule, BikeTrackerNetworkInterface bikeTrackerNetworkInterface) {
        this.bikesNetworkInterface = bikesNetworkInterface;
        this.loginManagerModule = loginManagerModule;
        this.bikesNetworkInterface.setModel(this);
        this.bikeTrackerNetworkInterface = bikeTrackerNetworkInterface;
    }

    @Override
    public void setPresenter(BikesMVP.Presenter presenter) {
        this.presenter = presenter;
    }

    public void getBikes(){
        //bikesNetworkInterface.getBikeArrayList(loginManagerModule.getUserId(), loginManagerModule.getToken());
        BikeTrackerList bikeTrackerList = BikeTrackerList.getInstance();
        bikeTrackerList.setBikeTrackerListListener(new BikeTrackerList.BikeTrackerListListener() {
            @Override
            public void listUpdated() {
                updateDone();
            }
        });
        bikeTrackerNetworkInterface.updateBike();
    }

//    @Override
//    public void setBikeArrayList(ArrayList<Pair<Bike, Tracker>> list) {
//        presenter.viewUpdate(list);
//    }


    @Override
    public void updateDone() {
        presenter.viewUpdate();
    }

    @Override
    public Throwable getError() {
        return null;
    }
}
