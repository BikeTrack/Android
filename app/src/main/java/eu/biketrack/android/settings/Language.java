package eu.biketrack.android.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

/**
 * Created by 42900 on 04/07/2017 for BikeTrack_Android.
 */

public class Language {
    public static void changeLanguage(Context context, Locale newLocale){
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(newLocale);
            resources.updateConfiguration(configuration,displayMetrics);

        } else {
            configuration.locale = newLocale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }
}
