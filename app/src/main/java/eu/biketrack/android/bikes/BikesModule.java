package eu.biketrack.android.bikes;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

@Module
public class BikesModule {
    @Provides
    public BikesMVP.Presenter provideBikesPresenter(BikesMVP.Model model) {
        return new BikesPresenter(model);
    }

    @Provides
    public BikesMVP.Model provideBikesModel(BikesNetworkInterface bikesNetworkInterface,
                                            LoginManagerModule loginManagerModule,
                                            BikeTrackerNetworkInterface bikeTrackerNetworkInterface) {
        return new BikesModel(bikesNetworkInterface, loginManagerModule, bikeTrackerNetworkInterface);
    }

    @Provides
    public BikesNetworkInterface provideBikesNetworkInterface(BiketrackService biketrackService) {
        return new BikesNetwork(biketrackService);
    }

}
