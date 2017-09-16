package eu.biketrack.android.login;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

@Module
public class LoginModule {
    @Provides
    public LoginMVP.Presenter provideLoginPresenter(LoginMVP.Model model){
        return new LoginPresenter(model);
    }

    @Provides
    public LoginMVP.Model provideLoginModel(LoginNetworkInterface loginNetworkInterface, LoginManagerModule loginManagerModule){
        return new LoginModel(loginNetworkInterface, loginManagerModule);
    }

    @Provides
    public LoginNetworkInterface provideLoginNetworkInterface(BiketrackService biketrackService){
        return new LoginNetwork(biketrackService);
    }
}