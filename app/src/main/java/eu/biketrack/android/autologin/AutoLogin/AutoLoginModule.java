package eu.biketrack.android.autologin.AutoLogin;

import dagger.Module;
import dagger.Provides;

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
    public AutoLoginMVP.Model provideAutoLoginModel(){
        return new AutoLoginModel();
    }
}
