package eu.biketrack.android.activities;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by 42900 on 07/02/2017 for BikeTrack_Android.
 */

public class BikeTrack
        extends Application {

    private static BikeTrack _instance;

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = (BikeTrack) getApplicationContext();

        // for better RxJava debugging
        //RxJavaHooks.enableAssemblyTracking();


        Timber.plant(new Timber.DebugTree());
    }
}