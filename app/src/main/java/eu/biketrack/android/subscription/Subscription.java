package eu.biketrack.android.subscription;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.autologin.AutoLogin;
import eu.biketrack.android.login.Login;
import eu.biketrack.android.root.App;
import io.reactivex.Observable;


public class Subscription extends Activity implements SubscriptionMVP.View{
    private static String TAG = "BIKETRACK - Subs";
    private boolean canSub = false;
    private boolean pwdMatch = false;
    private boolean pwdWeak = false;
    private boolean emailValid = false;


    @Inject
    SubscriptionMVP.Presenter presenter;

    @BindView(R.id.subscribtion_email_textview) EditText _email;
    @BindView(R.id.subscribtion_password_textview) EditText _password;
    @BindView(R.id.subscribtion_password_textview_repeat) EditText _password_repeat;
    @BindView((R.id.subscribtion_subscribe_button)) Button subscribe_button;
    @BindView(R.id.subscribtion_coordinator_layout)
    CoordinatorLayout coordinatorLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscribtion);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

//        subscribe_button.setEnabled(false);


        Observable<Boolean> emailObservable = RxTextView.textChanges(_email)
                .map(email -> email.toString().matches(Statics.REGEXP_EMAIL))
                .map(aBoolean -> emailValid = aBoolean);


        //Validate password field
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(_password);
        Observable<Boolean> securePasswordObservable = RxTextView.textChanges(_password).map(charSequence -> charSequence.length() >= 6)
                .map(aBoolean -> pwdWeak = aBoolean);

//Validate confirm password field
        Observable<CharSequence> confirmPasswordObservable = RxTextView.textChanges(_password_repeat);

//Validate password matches field
        Observable<Boolean> passwordMatcherObservable = Observable.combineLatest(passwordObservable, confirmPasswordObservable,
                (password, confirmPassword) -> password.toString().equals(confirmPassword.toString()))
                .map(aBoolean -> pwdMatch = aBoolean);

        Observable.combineLatest(passwordMatcherObservable, emailObservable, securePasswordObservable,
                (passwordMatch, isEmailValid, securePassword) ->
                    passwordMatch && isEmailValid && securePassword)
                .distinctUntilChanged()
                .subscribe(valid -> {
                    Log.d(TAG, "onCreate: " + valid);
                    canSub = valid;
//                    subscribe_button.setEnabled(valid);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @OnClick(R.id.subscribtion_subscribe_button)
    public void subscribeButtonOnClick(){
        Resources res = getResources();
        if (canSub)
            presenter.subscriptionButtonClicked();
        else{
            if (!emailValid)
                displayError(res.getString(R.string.error_check_email));
            else if (!pwdMatch)
                displayError(res.getString(R.string.error_check_password_and_repeat));
            else if (!pwdWeak)
                displayError(res.getString(R.string.error_check_password_secure));
        }

    }

    @OnClick(R.id.subscribtion_login_button)
    public void loginButtonOnClick(){
        presenter.loginButtonClicked();
    }

    @Override
    public String getUserEmail() {
        return _email.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return _password.getText().toString();
    }

    @Override
    public String getUserRepeatPassword() {
        return _password_repeat.getText().toString();
    }

    @Override
    public void close() {
        Intent intent = new Intent(this, AutoLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void displayError(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }
}
