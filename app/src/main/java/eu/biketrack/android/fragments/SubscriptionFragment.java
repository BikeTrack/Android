package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import hu.akarnokd.rxjava.interop.RxJavaInterop;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class SubscriptionFragment extends Fragment {
    private static String TAG = "BIKETRACK - Subs";
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private Disposable _disposable_email;
    private Disposable _disposable_password;
    private Disposable _disposable_password_repeat;
    private Unbinder unbinder;
    private CallbackManager callbackManager;

    private Boolean error_password_email = true;

    private static String REGEXP_EMAIL = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*)@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]+)\\])";

    @BindView(R.id.subscribtion_email_textview) EditText _email;
    @BindView(R.id.subscribtion_password_textview) EditText _password;
    @BindView(R.id.subscribtion_password_textview_repeat) EditText _password_repeat;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //email
        _disposable_email = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_email))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getCheckEmailObserver());

        //password security
        _disposable_password = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_password))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getCheckSecurePasswordObserver());

        //passwords equality
        _disposable_password_repeat = RxJavaInterop.toV2Observable(RxTextView.textChangeEvents(_password_repeat))
                .debounce(400, TimeUnit.MILLISECONDS)
                .filter(changes -> !TextUtils.isEmpty(changes.text().toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(_getCheckPasswordObserver());
    }

    private DisposableObserver<TextViewTextChangeEvent> _getCheckEmailObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error while checking email", e);
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                if (!onTextChangeEvent.text().toString().matches(REGEXP_EMAIL)) {
                    _email.setError(getActivity().getString(R.string.error_check_email));
                    error_password_email = true;
                } else
                    error_password_email = false;
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> _getCheckSecurePasswordObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error while checking password security", e);
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                if (onTextChangeEvent.text().toString().length() < 5) {
                    _password.setError(getActivity().getString(R.string.error_check_password_secure));
                    error_password_email = true;
                } else
                    error_password_email = false;
            }
        };
    }

    private DisposableObserver<TextViewTextChangeEvent> _getCheckPasswordObserver() {
        return new DisposableObserver<TextViewTextChangeEvent>() {
            @Override
            public void onComplete() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Error while checking password equality", e);
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                if (!onTextChangeEvent.text().toString().equals(_password.getText().toString())) {
                    _password_repeat.setError(getActivity().getString(R.string.error_check_password_and_repeat));
                    error_password_email = true;
                } else
                    error_password_email = false;
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_subscribtion, container, false);
        unbinder = ButterKnife.bind(this, layout);

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

    @OnClick(R.id.subscribtion_subscribe_button)
    public void subscribe(){
        if (error_password_email){
            Toast.makeText(getActivity(), R.string.error_subscribe_fields, Toast.LENGTH_SHORT).show();
            return;
        }
        _disposables.add(
                biketrackService.createUser(Statics.TOKEN_API, new AuthUser(_email.getText().toString(), _password.getText().toString()))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<SignupReception>(){

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Login completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred during login", e);
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(SignupReception signupReception) {
                                Log.d(TAG, signupReception.toString());
                            }
                        })
        );
    }

    @OnClick(R.id.subscribtion_login_button)
    public void login(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
