package eu.biketrack.android.autologin;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.session.Session;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Module
public class AutoLoginModule {
    @Provides
    public AutoLoginMVP.Presenter provideAutoLoginPresenter(AutoLoginMVP.Model model){
        return new AutoLoginPresenter(model);
    }

    @Provides
    public AutoLoginMVP.Model provideAutoLoginModel(AutoLoginNetworkInterface autoLoginNetworkInterface, Session session){
        return new AutoLoginModel(autoLoginNetworkInterface, session);
    }

    @Provides
    public AutoLoginNetworkInterface provideAutoLoginNetworkInterface(BiketrackService biketrackService){
        return new AutoLoginNetwork(biketrackService);
    }
}
