package eu.biketrack.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import org.json.JSONObject;

import eu.biketrack.android.R;
import retrofit2.HttpException;

/**
 * Created by SPARE on 10/01/2018.
 */

public class ErrorManager {
    private static final String TAG = "ErrorManager";
    private Context context;
    private Resources resources;

    public ErrorManager(Context context, Resources resources) {
        this.context = context;
        this.resources = resources;
    }

    public String getMessageFromThrowable(Throwable throwable){
        String tmp = null;
        if (throwable instanceof HttpException){
            try{
                JSONObject jObjError = new JSONObject(((HttpException) throwable).response().errorBody().string());
                Log.d(TAG, "getMessageFromThrowable: " + jObjError.toString());
                tmp = jObjError.getString("message");
                if (tmp.equals("email || password blank"))
                    tmp = resources.getString(R.string.user_not_found);
                else if (tmp.equals("Authentication failed. User not found."))
                    tmp = resources.getString(R.string.user_unauthorized);
                else if (tmp.equals("Authentication failed. Wrong password."))
                    tmp = resources.getString(R.string.error_incorrect_password);
                else if (tmp.equals("User already in the DB"))
                    tmp = resources.getString(R.string.user_already_created);
            } catch (Exception e){
                Log.e(TAG, "getMessageFromThrowable: ", e);
            }
            if (tmp == null){
                tmp = throwable.getMessage();
            }
        } else {
            if (throwable.getMessage().contains("Failed to connect"))
                tmp = "Connexion impossible. Vérifiez votre réseau.";
            else
                tmp = throwable.getMessage();
        }
        return tmp;
    }
}
