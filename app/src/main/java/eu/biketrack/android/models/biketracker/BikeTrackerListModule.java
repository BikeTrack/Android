package eu.biketrack.android.models.biketracker;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 28/10/2017 for BikeTrack_Android.
 */
@Module
public class BikeTrackerListModule {
    @Provides
    public BikeTrackerNetworkInterface provideBikeTrackerNetworkInterface(LoginManagerModule loginManagerModule, BiketrackService biketrackService) {
        return new BikeTrackerNetwork(loginManagerModule, biketrackService);
    }
}
