package eu.biketrack.android.root;

import android.app.Application;

import eu.biketrack.android.autologin.al_activity.AutoLoginActModule;

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
                .autoLoginActModule(new AutoLoginActModule())
                .build();
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
