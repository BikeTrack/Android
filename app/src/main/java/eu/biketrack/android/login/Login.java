package eu.biketrack.android.login;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.autologin.AutoLogin;
import eu.biketrack.android.root.App;
import eu.biketrack.android.subscription.Subscription;


public class Login extends Activity implements LoginMVP.View {
    private static final String TAG = "Login";
    private CallbackManager  callbackManager;

    @Inject
    LoginMVP.Presenter presenter;


    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.login_email_textview)
    EditText _email;
    @BindView(R.id.login_password_textview)
    EditText _password;
    @BindView(R.id.login_facebook_button)
    LoginButton _facebook_button;
    @BindView(R.id.login_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.login_login_button)
    Button loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        ButterKnife.bind(this);

//        _email.setText("thisisatest@test.com");
//        _password.setText("azerty");

        callbackManager = CallbackManager.Factory.create();
        _facebook_button.setReadPermissions("email");
        _facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "Facebook success ");
                Log.d(TAG, loginResult.getAccessToken().getApplicationId() + "\n"
                        + loginResult.getAccessToken().getToken() + "\n"
                        + loginResult.getAccessToken().getLastRefresh() + "\n"
                        + loginResult.getAccessToken().getExpires());
                Log.d(TAG, loginResult.getRecentlyDeniedPermissions().toString());
                Log.d(TAG, loginResult.getRecentlyGrantedPermissions().toString());
                Log.d(TAG, loginResult.getAccessToken().getUserId());
                Log.d(TAG, loginResult.getAccessToken().getSource().toString());
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.d(TAG, object.toString());
                                Log.d(TAG, response.toString());
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,first_name,gender,last_name,location,email");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                Log.d(TAG, "Facebook cancel");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.e(TAG, "Facebook error", exception);
            }
        });

        ((App) getApplication()).getComponent().inject(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loading(false);
        presenter.setView(this);
    }

    @OnClick(R.id.login_login_button)
    public void loginButtonOnClick() {
        presenter.connexionButtonClicked();
    }

    @OnClick(R.id.login_subscribe_button)
    public void subscribeButtonOnClick(){presenter.goToSubscribe();}

    @Override
    public String getUserEmail() {
        return _email.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return _password.getText().toString();
    }

    @Override
    public void close() {
        Intent intent = new Intent(this, AutoLogin.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openSubscribe() {
        Intent intent = new Intent(this, Subscription.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void loading(boolean loading) {
        if (loading){
            loginButton.setClickable(false);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            loginButton.setClickable(true);
            progressBar.setVisibility(View.GONE);
        }
    }
}
