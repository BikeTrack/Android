package eu.biketrack.android.subscription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.zxing.common.StringUtils;
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

    @Inject
    SubscriptionMVP.Presenter presenter;

    @BindView(R.id.subscribtion_email_textview) EditText _email;
    @BindView(R.id.subscribtion_password_textview) EditText _password;
    @BindView(R.id.subscribtion_password_textview_repeat) EditText _password_repeat;
    @BindView((R.id.subscribtion_subscribe_button)) Button subscribe_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscribtion);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        subscribe_button.setEnabled(false);


        Observable<Boolean> emailObservable = RxTextView.textChanges(_email)
                .map(email -> email.toString().matches(Statics.REGEXP_EMAIL)).skip(1);


        //Validate password field
        Observable<CharSequence> passwordObservable = RxTextView.textChanges(_password).skip(1);

//Validate confirm password field
        Observable<CharSequence> confirmPasswordObservable = RxTextView.textChanges(_password_repeat)
                .skip(1);

//Validate password matches field
        Observable<Boolean> passwordMatcherObservable = Observable.combineLatest(passwordObservable, confirmPasswordObservable,
                (password, confirmPassword) -> password.toString().equals(confirmPassword.toString())).skip(1);

        Observable.combineLatest(passwordMatcherObservable, emailObservable,
                (passwordMatch, isEmailValid) -> passwordMatch && isEmailValid)
                .distinctUntilChanged()
                .subscribe(valid -> {
                    Log.d(TAG, "onCreate: " + valid);
                    subscribe_button.setEnabled(valid);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @OnClick(R.id.subscribtion_subscribe_button)
    public void subscribeButtonOnClick(){
        presenter.subscriptionButtonClicked();
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
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
