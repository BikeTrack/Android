package eu.biketrack.android.activities;

import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import eu.biketrack.android.R;

public class AutoLogin extends AppCompatActivity {
    private AccountManager am;
    private Bundle options = new Bundle();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_login);

        Intent mainactivity_intent = new Intent(this, MainActivity.class);
        startActivity(mainactivity_intent);
//        am = AccountManager.get(this);
//
//        am.getAuthToken(
//                myAccount_,                     // Account retrieved using getAccountsByType()
//                "Manage your tasks",            // Auth scope
//                options,                        // Authenticator-specific options
//                this,                           // Your activity
//                new OnTokenAcquired(),          // Callback called when a token is successfully acquired
//                new Handler(new OnError()));    // Callback called if an error occurs
    }
//
//    private class OnTokenAcquired implements AccountManagerCallback<Bundle> {
//        @Override
//        public void run(AccountManagerFuture<Bundle> result) {
//            // Get the result of the operation from the AccountManagerFuture.
//            Bundle bundle = result.getResult();
//
//            // The token is a named value in the bundle. The name of the value
//            // is stored in the constant AccountManager.KEY_AUTHTOKEN.
//            String token = bundle.getString(AccountManager.KEY_AUTHTOKEN);
//        }
//    }
//
}
