package eu.biketrack.android.initializer;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Module
public class InitializerModule {
    @Provides
    public InitializerMVP.Presenter provideAutoLoginActivityPresenter(InitializerMVP.Model model) {
        return new InitializerPresenter(model);
    }

    @Provides
    public InitializerMVP.Model provideAutoLoginActivityModel(LoginManagerModule loginManager) {
        return new InitializerModel(loginManager);
    }

}
