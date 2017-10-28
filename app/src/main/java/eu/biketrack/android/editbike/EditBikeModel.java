package eu.biketrack.android.editbike;

import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 28/10/2017 for BikeTrack_Android.
 */

public class EditBikeModel implements EditBikeMVP.Model {
    private LoginManagerModule loginManagerModule;
    private BikeTrackerNetworkInterface bikeTrackerNetworkInterface;

    public EditBikeModel(LoginManagerModule loginManagerModule, BikeTrackerNetworkInterface bikeTrackerNetworkInterface) {
        this.loginManagerModule = loginManagerModule;
        this.bikeTrackerNetworkInterface = bikeTrackerNetworkInterface;
    }

    @Override
    public void createBike(SendBikeInfo bike) {
        bikeTrackerNetworkInterface.createBike(bike);
    }
}
