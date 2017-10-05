package eu.biketrack.android.root;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.initializer.InitializerModule;
import eu.biketrack.android.autologin.AutoLoginModule;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class App extends Application {

    private ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .initializerModule(new InitializerModule())
                .autoLoginModule(new AutoLoginModule())
                .apiConnectModule(new ApiConnectModule())
                .build();


        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
