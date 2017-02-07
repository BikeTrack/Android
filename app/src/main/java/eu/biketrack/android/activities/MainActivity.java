package eu.biketrack.android.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import eu.biketrack.android.R;
import eu.biketrack.android.fragments.LoginFragment;

public class MainActivity extends FragmentActivity {

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, new LoginFragment(), this.toString())
                    .commit();
        }
    }
}
