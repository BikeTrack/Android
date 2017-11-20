package eu.biketrack.android.settings.profile_tab;

import android.provider.ContactsContract;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.bikes.BikesMVP;
import eu.biketrack.android.bikes.BikesModel;
import eu.biketrack.android.bikes.BikesNetwork;
import eu.biketrack.android.bikes.BikesNetworkInterface;
import eu.biketrack.android.bikes.BikesPresenter;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

@Module
public class ProfileModule {
    @Provides
    public ProfileMVP.Presenter provideBikesPresenter(ProfileMVP.Model model){
        return new ProfilePresenter(model);
    }

    @Provides
    public ProfileMVP.Model provideBikesModel(ProfileNetworkInterface profileNetworkInterface,
                                            LoginManagerModule loginManagerModule){
        return new ProfileModel(profileNetworkInterface, loginManagerModule);
    }

    @Provides
    public ProfileNetworkInterface provideBikesNetworkInterface(BiketrackService biketrackService){
        return new ProfileNetwork(biketrackService);
    }
}
