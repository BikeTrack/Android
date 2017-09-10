package eu.biketrack.android.login;


import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

import butterknife.BindView;
import eu.biketrack.android.R;


public class LoginFragment extends Activity {
    private static final String TAG = "LoginFragment";
//    private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
//    private LoginManagerModule loginManagerModule;
//    private Unbinder unbinder;
//    CallbackManager callbackManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.login_email_textview)
    EditText _email;
    @BindView(R.id.login_password_textview)
    EditText _password;
//    @BindView(R.id.login_facebook_button)
//    LoginButton _facebook_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getActivity().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
//        loginManagerModule = LoginManagerModule.getInstance();
//        callbackManager = CallbackManager.Factory.create();
    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_login, container, false);
//        unbinder = ButterKnife.bind(this, layout);
////        _facebook_button.setReadPermissions("email");
////        _facebook_button.setFragment(this);
////
////        _facebook_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
////            @Override
////            public void onSuccess(LoginResult loginResult) {
////                // App code
////                Log.d(TAG, "Facebook success ");
////                Log.d(TAG, loginResult.getAccessToken().getApplicationId() + "\n"
////                        + loginResult.getAccessToken().getToken() + "\n"
////                        + loginResult.getAccessToken().getLastRefresh() + "\n"
////                        + loginResult.getAccessToken().getExpires());
////                Log.d(TAG, loginResult.getRecentlyDeniedPermissions().toString());
////                Log.d(TAG, loginResult.getRecentlyGrantedPermissions().toString());
////                Log.d(TAG, loginResult.getAccessToken().getUserId());
////                Log.d(TAG, loginResult.getAccessToken().getSource().toString());
////                GraphRequest request = GraphRequest.newMeRequest(
////                        loginResult.getAccessToken(),
////                        new GraphRequest.GraphJSONObjectCallback() {
////                            @Override
////                            public void onCompleted(
////                                    JSONObject object,
////                                    GraphResponse response) {
////                                Log.d(TAG, object.toString());
////                                Log.d(TAG, response.toString());
////                            }
////                        });
////                Bundle parameters = new Bundle();
////                parameters.putString("fields", "id,name,link,birthday,first_name,gender,last_name,location,email");
////                request.setParameters(parameters);
////                request.executeAsync();
////            }
////
////            @Override
////            public void onCancel() {
////                // App code
////                Log.d(TAG, "Facebook cancel");
////            }
////
////            @Override
////            public void onError(FacebookException exception) {
////                // App code
////                Log.e(TAG, "Facebook error", exception);
////            }
////        });
////        _email.setText("thisisatest@test.com");
////        _password.setText("azerty");
//        return layout;
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnClick(R.id.login_login_button)
//    public void login() {
//        _disposables.add(
//                biketrackService.connectUser(Statics.TOKEN_API, new AuthUser(_email.getText().toString(), _password.getText().toString()))
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<AuthenticateReception>() {
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Login completed");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred during login", e);
//                                //check error type and raise toast
//                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
//                                    Toast.makeText(getActivity(), R.string.user_unauthorized, Toast.LENGTH_SHORT).show();
//                                else if (e.getMessage().equals("HTTP 404 Not Found"))
//                                    Toast.makeText(getActivity(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//
//                            }
//
//                            @Override
//                            public void onNext(AuthenticateReception authenticateReception) {
//                                //Log.d(TAG, authenticateReception.toString());
//                                if (loginManagerModule != null) {
//                                    loginManagerModule.storeEmail(_email.getText().toString());
//                                    loginManagerModule.storeUserId(authenticateReception.getUserId());
//                                    loginManagerModule.storeToken(authenticateReception.getToken());
//                                }
//                                closeFragment();
//                            }
//                        })
//        );
//    }
//
//    @OnClick(R.id.login_subscribe_button)
//    public void subscribe() {
//        Fragment fragment = new SubscriptionFragment();
//        final String tag = fragment.getClass().toString();
//        Log.d(TAG, tag);
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack(tag)
//                .replace(android.R.id.content, fragment, tag)
//                .commit();
//
//    }
//
//    private void closeFragment(){
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new AutoLogin(), this.toString())
//                .commit();
//    }


}
