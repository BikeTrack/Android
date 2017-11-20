package eu.biketrack.android.settings.profile_tab;

import eu.biketrack.android.api_connection.BiketrackService;

/**
 * Created by 42900 on 20/11/2017 for BikeTrack_Android.
 */

public class ProfileNetwork implements ProfileNetworkInterface {
    private BiketrackService biketrackService;

    public ProfileNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }
}
