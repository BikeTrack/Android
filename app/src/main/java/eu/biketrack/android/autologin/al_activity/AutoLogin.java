package eu.biketrack.android.autologin.al_activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import java.util.Locale;

import javax.inject.Inject;

import eu.biketrack.android.R;
import eu.biketrack.android.autologin.al_fragment.AutoLoginFragment;
import eu.biketrack.android.root.App;
import eu.biketrack.android.session.LoginManager;
import eu.biketrack.android.settings.Language;

public class AutoLogin extends FragmentActivity implements AutoLoginActMVP.View{
    private static final String TAG = "AutoLogin";

    @Inject
    AutoLoginActMVP.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);

        ((App) getApplication()).getComponent().inject(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.initApplication(this);
    }



    @Override
    public void openAutoLoginFragment(){
        getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new AutoLoginFragment(), this.toString())
                .commit();
    }
}
