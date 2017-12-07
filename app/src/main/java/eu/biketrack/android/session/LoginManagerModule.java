package eu.biketrack.android.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by 42900 on 21/06/2017 for BikeTrack_Android.
 */

@Singleton
public class LoginManagerModule{
    private static final String TAG = "LoginManagerModule";
    private SharedPreferences sharedPreferences;
    private static final String KEY_EMAIL = "eu.biketrack.android.email";
    private static final String KEY_USERID = "eu.biketrack.android.userid";
    private static final String KEY_PASSWORD = "eu.biketrack.android.password";
    private static final String KEY_TOKEN = "eu.biketrack.android.token";

    public static final String SENT_TOKEN_TO_SERVER = "sentTokenToServer";
    public static final String REGISTRATION_COMPLETE = "registrationComplete";
    private Context context;

    @Inject
    public LoginManagerModule(Context context){
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("eu.biketrack.android", Context.MODE_PRIVATE);
        }
        this.context = context;
    }

    public void storeEmail(String email){
        sharedPreferences.edit().putString(KEY_EMAIL, email).apply();
        Log.d(TAG, "storeEmail: " + email);
    }

    public String getEmail(){
        return sharedPreferences.getString(KEY_EMAIL, null);
    }

    public void storeUserId(String userId){
        sharedPreferences.edit().putString(KEY_USERID, userId).apply();
        Log.d(TAG, "storeUserId: " + userId);
    }

    public String getUserId(){
        return sharedPreferences.getString(KEY_USERID, null);
    }

    public void storePassword(String password){
        sharedPreferences.edit().putString(KEY_PASSWORD, password).apply();
    }

    public String getPassword(){
        return sharedPreferences.getString(KEY_PASSWORD, null);
    }

    public void storeToken(String token){
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
        Log.d(TAG, "storeToken: " + token);
    }

    public String getToken(){
        return sharedPreferences.getString(KEY_TOKEN, null);
    }

    public void clear(){
        sharedPreferences.edit().clear().apply();
        Log.d(TAG, "cleared ");
    }

    public Context getContext() {
        return context;
    }
}

