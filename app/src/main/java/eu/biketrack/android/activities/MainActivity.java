package eu.biketrack.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import eu.biketrack.android.fragments.LoginFragment;

public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
//            Intent autolog_activity_intent = new Intent(this, AutoLogin.class);
//            autolog_activity_intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//            startActivity(autolog_activity_intent);
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new LoginFragment(), this.toString())
                    .commit();
        }
    }
}
