package eu.biketrack.android.models.biketracker;

import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;

/**
 * Created by 42900 on 27/10/2017 for BikeTrack_Android.
 */

public interface BikeTrackerNetworkInterface {
    void updateBike();
    void createBike(SendBikeInfo bike);
}
