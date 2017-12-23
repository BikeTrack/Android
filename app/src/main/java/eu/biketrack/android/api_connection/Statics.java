package eu.biketrack.android.api_connection;

/**
 * Created by adrienschricke on 17/09/2016.
 */
public class Statics {
    public static final String ROOT_API = "http://163.172.81.184:3000/";
//    public static final String ROOT_API = "https://bike-track-api.herokuapp.com/";
    public static final String TOKEN_API = "9E34BA33E63D1C25AC7834971C211139936C68394FEBE2BC2EA5B5F7F8664F0";

    public static String REGEXP_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]+)\\])";

    public static final double BATTERY_CRITICAL = 10.0;
    public static final double BATTERY_LOW = 30.0;

    public static final int MAX_POINTS_DISPLAYED_ON_MAP = 20;

}
