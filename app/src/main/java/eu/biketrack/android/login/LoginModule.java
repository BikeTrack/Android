package eu.biketrack.android.login;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

@Module
public class LoginModule {
    @Provides
    public LoginMVP.Presenter provideAutoLoginPresenter(LoginMVP.Model model){
        return new LoginPresenter(model);
    }

    @Provides
    public LoginMVP.Model provideAutoLoginModel(){
        return new LoginModel();
    }
}