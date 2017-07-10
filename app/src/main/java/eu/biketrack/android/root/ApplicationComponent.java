package eu.biketrack.android.root;

import javax.inject.Singleton;

import dagger.Component;
import eu.biketrack.android.activities.BikeTrack;
import eu.biketrack.android.autologin.al_activity.AutoLogin;
import eu.biketrack.android.autologin.al_activity.AutoLoginActModule;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        AutoLoginActModule.class
})
public interface ApplicationComponent {

    void inject(BikeTrack target);
    void inject(AutoLogin target);
}
