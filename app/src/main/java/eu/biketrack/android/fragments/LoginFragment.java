package eu.biketrack.android.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_send.AuthUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class LoginFragment extends Fragment {
    private static String TAG = "BIKETRACK - Login";
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private Unbinder unbinder;
    CallbackManager callbackManager;

    @BindView(R.id.login_email_textview) EditText _email;
    @BindView(R.id.login_password_textview) EditText _password;
    @BindView(R.id.login_facebook_button) LoginButton _facebook_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        unbinder = ButterKnife.bind(this, layout);

        _facebook_button.setFragment(this);
        _facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.d(TAG, "Facebook success ");
                Log.d(TAG, loginResult.getAccessToken().getApplicationId() + "\n"
                        + loginResult.getAccessToken().getToken() + "\n"
                        + loginResult.getAccessToken().getLastRefresh() + "\n"
                        + loginResult.getAccessToken().getExpires());
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

        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.login_login_button)
    public void login(){
        _disposables.add(
                biketrackService.connectUser(Statics.TOKEN_API, new AuthUser(_email.getText().toString(), _password.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<AuthenticateReception>(){

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Login completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred during login", e);
                                //check error type and raise toast
                            }

                            @Override
                            public void onNext(AuthenticateReception authenticateReception) {
                                Log.d(TAG, authenticateReception.toString());
                            }
                        })
        );
    }

    @OnClick(R.id.login_subscribe_button)
    public void subscribe(){
        Fragment fragment = new SubscriptionFragment();
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();

    }
}
