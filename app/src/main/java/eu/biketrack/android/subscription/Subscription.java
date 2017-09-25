package eu.biketrack.android.subscription;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.autologin.AutoLogin.AutoLogin;
import eu.biketrack.android.login.Login;
import eu.biketrack.android.root.App;


public class Subscription extends Activity implements SubscriptionMVP.View{
    private static String TAG = "BIKETRACK - Subs";
//    private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
//    private Disposable _disposable_email;
//    private Disposable _disposable_password;
//    private Disposable _disposable_password_repeat;
//    private LoginManagerModule loginManagerModule;
//
//    private Boolean error_password_email = true;

    @Inject
    SubscriptionMVP.Presenter presenter;



    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.subscribtion_email_textview) EditText _email;
    @BindView(R.id.subscribtion_password_textview) EditText _password;
    @BindView(R.id.subscribtion_password_textview_repeat) EditText _password_repeat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscribtion);
        ButterKnife.bind(this);

        ((App) getApplication()).getComponent().inject(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
//        callbackManager = CallbackManager.Factory.create();
//        loginManagerModule = LoginManagerModule.getInstance();
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

    //    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        //email
//        _disposable_email = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_email))
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(_getCheckEmailObserver());
//
//        //password security
//        _disposable_password = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_password))
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(_getCheckSecurePasswordObserver());
//
//        //passwords equality
//        _disposable_password_repeat = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_password_repeat))
//                .debounce(400, TimeUnit.MILLISECONDS)
//                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(_getCheckPasswordObserver());
//    }
//
//    private DisposableObserver<TextViewTextChangeEvent> _getCheckEmailObserver() {
//        return new DisposableObserver<TextViewTextChangeEvent>() {
//            @Override
//            public void onComplete() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "Error while checking email", e);
//            }
//
//            @Override
//            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                if (!onTextChangeEvent.text().toString().matches(Statics.REGEXP_EMAIL)) {
//                    _email.setError(getActivity().getString(R.string.error_check_email));
//                    error_password_email = true;
//                } else
//                    error_password_email = false;
//            }
//        };
//    }
//
//    private DisposableObserver<TextViewTextChangeEvent> _getCheckSecurePasswordObserver() {
//        return new DisposableObserver<TextViewTextChangeEvent>() {
//            @Override
//            public void onComplete() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "Error while checking password security", e);
//            }
//
//            @Override
//            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                if (onTextChangeEvent.text().toString().length() < 5) {
//                    _password.setError(getActivity().getString(R.string.error_check_password_secure));
//                    error_password_email = true;
//                } else
//                    error_password_email = false;
//            }
//        };
//    }
//
//    private DisposableObserver<TextViewTextChangeEvent> _getCheckPasswordObserver() {
//        return new DisposableObserver<TextViewTextChangeEvent>() {
//            @Override
//            public void onComplete() {
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e(TAG, "Error while checking password equality", e);
//            }
//
//            @Override
//            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
//                if (!onTextChangeEvent.text().toString().equals(_password.getText().toString())) {
//                    _password_repeat.setError(getActivity().getString(R.string.error_check_password_and_repeat));
//                    error_password_email = true;
//                } else
//                    error_password_email = false;
//            }
//        };
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_subscribtion, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        return layout;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @OnClick(R.id.subscribtion_subscribe_button)
//    public void subscribe(){
//        if (error_password_email){
//            Toast.makeText(getActivity(), R.string.error_subscribe_fields, Toast.LENGTH_SHORT).show();
//            return;
//        }
//        AuthUser authUser = new AuthUser(_email.getText().toString(), _password.getText().toString());
//        _disposables.add(
//                biketrackService.createUser(Statics.TOKEN_API, authUser)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<SignupReception>(){
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Login completed");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                //check error type and raise toast
//                                Log.d(TAG, e.getMessage());
//                                if (e.getMessage().equals("HTTP 409 Conflict"))
//                                    Toast.makeText(getActivity(), R.string.user_already_created, Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(SignupReception signupReception) {
//                                Log.d(TAG, signupReception.toString());
//                                login(authUser);
//                            }
//                        })
//        );
//    }
//
//
//    private void login(AuthUser authUser){
//        _disposables.add(biketrackService.connectUser(Statics.TOKEN_API, authUser)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(new DisposableObserver<AuthenticateReception>(){
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "Login completed");
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        //check error type and raise toast
//                        if (e.getMessage().equals("HTTP 401 Unauthorized"))
//                            Toast.makeText(getActivity(), R.string.user_unauthorized, Toast.LENGTH_SHORT).show();
//                        else if (e.getMessage().equals("HTTP 404 Not Found"))
//                            Toast.makeText(getActivity(), R.string.user_not_found, Toast.LENGTH_SHORT).show();
//                        else
//                            Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    @Override
//                    public void onNext(AuthenticateReception authenticateReception) {
//                        if (loginManagerModule != null) {
//                            loginManagerModule.storeEmail(_email.getText().toString());
//                            loginManagerModule.storeUserId(authenticateReception.getUserId());
//                            loginManagerModule.storeToken(authenticateReception.getToken());
//                        }
//                        closeFragment();
//                    }
//                }));
//    }
//
//
//    @OnClick(R.id.subscribtion_login_button)
//    public void backTologin(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }
//
//    private void closeFragment(){
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new AutoLoginFragment(), this.toString())
//                .commit();
//    }

}
