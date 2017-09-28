package eu.biketrack.android.bikes;

import eu.biketrack.android.api_connection.BiketrackService;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public class BikesNetwork implements BikesNetworkInterface{
    private static final String TAG = "BikesNetwork";
    private BiketrackService biketrackService;

    public BikesNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }
}
