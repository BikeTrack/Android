package eu.biketrack.android.editbike;

import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
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

    @Override
    public void updateBike(String bikeId, SendBikeInfo bike) {
        bikeTrackerNetworkInterface.updateBike(bikeId, bike);
    }

    @Override
    public void deleteBike(String bikeId, SendBikeInfo bike) {
        bikeTrackerNetworkInterface.deleteBike(bikeId, bike);
    }

    @Override
    public void uploadBikePhoto(String uri, String bikeId) {
        bikeTrackerNetworkInterface.uploadBikePicture(uri, bikeId);
    }

    @Override
    public void uploadBikeBill(String uri, String bikeId) {
        bikeTrackerNetworkInterface.uploadBikeBill(uri, bikeId);
    }
}
