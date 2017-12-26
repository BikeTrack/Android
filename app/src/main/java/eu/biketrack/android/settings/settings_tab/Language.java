package eu.biketrack.android.settings.settings_tab;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import java.util.Locale;

/**
 * Created by 42900 on 04/07/2017 for BikeTrack_Android.
 */

public class Language {
    private static final String TAG = "Language";
    private static final String KEY_LANGUAGE = "eu.biketrack.android.lang";
    private static final String KEY_DEFAULT = "eu.biketrack.android.lang.default";

//Locale.getDefault().getDisplayLanguage();
//Locale current = getResources().getConfiguration().locale;

    public static void changeLanguage(Context context, Locale newLocale){
        if (getDefaultLocale(context) == null)
            setDefaultLocale(context);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(newLocale);
            resources.updateConfiguration(configuration, displayMetrics);
        } else {
            configuration.locale = newLocale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_LANGUAGE, newLocale.getLanguage()).apply();
    }

    public static void setDefaultLocale(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_DEFAULT, Locale.getDefault().getLanguage()).apply();
    }

    public static String getDefaultLocale(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_DEFAULT, null);
    }

    public static String getCurrentLanguage(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LANGUAGE, null);
    }

    public static void clearLanguage(Context context){
        Locale newLocale = new Locale(getDefaultLocale(context));
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLocale(newLocale);
            resources.updateConfiguration(configuration, displayMetrics);
        } else {
            configuration.locale = newLocale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        SharedPreferences sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(KEY_LANGUAGE, newLocale.getLanguage()).apply();
    }


}
