package eu.biketrack.android.autologin.al_activity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Module
public class AutoLoginActModule {
    private static final String TAG = "AutoLoginActModule";

    @Provides
    public AutoLoginActMVP.Presenter provideAutoLoginActivityPresenter(AutoLoginActMVP.Model model){
        return new AutoLoginActPresenter(model);
    }

    @Provides
    public AutoLoginActMVP.Model provideAutoLoginActivityModel(){
        return new AutoLoginActModel();
    }

}
