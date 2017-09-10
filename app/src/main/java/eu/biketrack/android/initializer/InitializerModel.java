package eu.biketrack.android.initializer;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

import javax.inject.Inject;

import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.settings.Language;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class InitializerModel implements InitializerMVP.Model {
    private static final String TAG = "InitializerModel";

    @Inject
    public LoginManagerModule loginManagerModule;

    public InitializerModel(LoginManagerModule loginManagerModule) {
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void language(Context context){
        if (Language.getCurrentLanguage(context) != null)
            Language.changeLanguage(context, new Locale(Language.getCurrentLanguage(context)));
        Log.d(TAG, "language: set");
    }

    @Override
    public void initLoginManager(Context context){
//        loginManagerModule.clear();
        Log.d(TAG, "initLoginManager: initialized");
    }
}
