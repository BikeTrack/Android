package eu.biketrack.android.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import eu.biketrack.android.R;
import eu.biketrack.android.fragments.AutoLoginFragment;
import eu.biketrack.android.session.LoginManager;

public class AutoLogin extends FragmentActivity {
    private static final String TAG = "Biketrack - AutoLogin";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);

        LoginManager loginManager = LoginManager.getInstance();
        loginManager.init(this);
        //loginManager.clear();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AutoLoginFragment(), this.toString())
                .commit();
    }
}