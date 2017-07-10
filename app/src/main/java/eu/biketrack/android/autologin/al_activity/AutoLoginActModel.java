package eu.biketrack.android.autologin.al_activity;

import android.content.Context;
import android.util.Log;

import java.util.Locale;

import eu.biketrack.android.session.LoginManager;
import eu.biketrack.android.settings.Language;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginActModel implements AutoLoginActMVP.Model {
    private static final String TAG = "AutoLoginActModel";

    @Override
    public void language(Context context){
        if (Language.getCurrentLanguage(context) != null)
            Language.changeLanguage(context, new Locale(Language.getCurrentLanguage(context)));
        Log.d(TAG, "language: set");
    }

    @Override
    public void initLoginManager(Context context){
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.init(context);
//        loginManager.clear();
        Log.d(TAG, "initLoginManager: initialized");
    }
}
