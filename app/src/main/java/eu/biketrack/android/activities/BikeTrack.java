package eu.biketrack.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import eu.biketrack.android.accident.AccidentActivity;
import eu.biketrack.android.initializer.Initializer;

/**
 * Created by 42900 on 07/02/2017 for BikeTrack_Android.
 */

public class BikeTrack extends Activity {
    private static final String TAG = "BikeTrack";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = new Bundle();
        try {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }

            bundle.putString("latitude", getIntent().getExtras().getString("latitude"));
            bundle.putString("longitude", getIntent().getExtras().getString("longitude"));
            bundle.putString("trackerId", getIntent().getExtras().getString("tarckerId"));
            bundle.putString("type", getIntent().getExtras().getString("type"));
        } catch (Exception e) {
            Log.e(TAG, "onCreate: ", e);
        }


        Intent autologin_intent = new Intent(this, Initializer.class);
        startActivity(autologin_intent);
        finish();

        try {
            if (bundle.getString("type").equals("accident")) {
                Intent accident = new Intent(this, AccidentActivity.class);
                accident.putExtras(bundle);
                startActivity(accident);
                finish();
            }
        } catch (Exception e) {
            Log.e(TAG, "onCreate2: ", e);
        }
    }
}