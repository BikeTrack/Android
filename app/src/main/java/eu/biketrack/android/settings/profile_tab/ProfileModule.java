package eu.biketrack.android.settings.profile_tab;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
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
