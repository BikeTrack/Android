package eu.biketrack.beta.session;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 42900 on 21/06/2017 for BikeTrack_Android.
 */

public class LoginManager {
    private static final LoginManager ourInstance = new LoginManager();
    private SharedPreferences sharedPreferences;
    private static final String KEY_EMAIL = "eu.biketrack.android.email";
    private static final String KEY_USERID = "eu.biketrack.android.userid";
    private static final String KEY_TOKEN = "eu.biketrack.android.token";

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";

    public static LoginManager getInstance() {
        return ourInstance;
    }

    private LoginManager(){}

    public void init(Context context){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        }
    }

    public void storeEmail(String email){
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply();
    }

    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void storeUserId(String userId){
        sharedPreferences.edit().putString(KEY_USERID, userId).apply();
    }

    public String getUserId(){
        return sharedPreferences.getString(KEY_USERID, null);
    }

    public void storeToken(String token){
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clear(){
        sharedPreferences.edit().clear().apply();
    }
}

