package eu.biketrack.android.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by 42900 on 07/02/2017 for BikeTrack_Android.
 */

public class BikeTrack extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent autologin_intent = new Intent(this, AutoLogin.class);
        startActivity(autologin_intent);
        finish();
    }
}