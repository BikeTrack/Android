package eu.biketrack.android.root;

import javax.inject.Singleton;

import dagger.Component;
import eu.biketrack.android.activities.BikeTrack;
import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.autologin.AutoLogin.AutoLogin;
import eu.biketrack.android.initializer.Initializer;
import eu.biketrack.android.initializer.InitializerModule;
import eu.biketrack.android.autologin.AutoLogin.AutoLoginModule;
import eu.biketrack.android.login.Login;
import eu.biketrack.android.login.LoginModule;
import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        InitializerModule.class,
        AutoLoginModule.class,
        ApiConnectModule.class,
        LoginModule.class
})
public interface ApplicationComponent {

    void inject(BikeTrack target);
    void inject(Initializer target);
    void inject(AutoLogin target);
    void inject(Login target);
    void inject(ApiConnectModule target);

    LoginManagerModule getLoginManagerModule();

    Session getSession();
}
