package eu.biketrack.android.bill;

import android.graphics.Bitmap;

import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 29/12/2017 for BikeTrack_Android.
 */

public class BillModel implements BillMVP.Model {
    private LoginManagerModule loginManagerModule;
    private BikeTrackerNetworkInterface bikeTrackerNetworkInterface;

    public BillModel(LoginManagerModule loginManagerModule, BikeTrackerNetworkInterface bikeTrackerNetworkInterface) {
        this.loginManagerModule = loginManagerModule;
        this.bikeTrackerNetworkInterface = bikeTrackerNetworkInterface;
    }

    @Override
    public void loadBill(String bikeId) {
        bikeTrackerNetworkInterface.downloadBikeBill(bikeId);
    }
}
