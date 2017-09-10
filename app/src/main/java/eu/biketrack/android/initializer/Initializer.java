package eu.biketrack.android.initializer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import eu.biketrack.android.R;
import eu.biketrack.android.autologin.AutoLogin.AutoLogin;
import eu.biketrack.android.root.App;

public class Initializer extends Activity implements InitializerMVP.View{
    private static final String TAG = "Initializer";

    @Inject
    InitializerMVP.Presenter presenter;

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
//        getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new AutoLogin(), this.toString())
//                .commit();
        Intent intent = new Intent(this, AutoLogin.class);
        startActivity(intent);
        finish();
    }
}
