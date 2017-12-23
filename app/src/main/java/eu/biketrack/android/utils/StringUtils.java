package eu.biketrack.android.utils;

/**
 * Created by 42900 on 05/07/2017 for BikeTrack_Android.
 */

public class StringUtils {
    public static Boolean isEmpty(String string){
        if (string == null)
            return true;
        if (string.length() == 0)
            return true;
        return false;
    }
}
