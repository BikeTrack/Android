package eu.biketrack.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;

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
                Log.d(TAG, "getMessageFromThrowable: " +((HttpException)throwable).response().raw().body().toString());
                tmp = ((HttpException)throwable).response().body().toString();
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
