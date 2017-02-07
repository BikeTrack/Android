package eu.biketrack.android.activities;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import timber.log.Timber;

/**
 * Created by 42900 on 07/02/2017 for BikeTrack_Android.
 */

public class BikeTrack
        extends Application {

    private static BikeTrack _instance;
    private RefWatcher _refWatcher;

    public static BikeTrack get() {
        return _instance;
    }

    public static RefWatcher getRefWatcher() {
        return BikeTrack.get()._refWatcher;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        _instance = (BikeTrack) getApplicationContext();
        _refWatcher = LeakCanary.install(this);

        // for better RxJava debugging
        //RxJavaHooks.enableAssemblyTracking();


        Timber.plant(new Timber.DebugTree());
    }
}