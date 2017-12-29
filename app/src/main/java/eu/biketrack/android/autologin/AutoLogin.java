package eu.biketrack.android.autologin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.biketrack.android.R;
import eu.biketrack.android.bikes.Bikes;
import eu.biketrack.android.login.Login;
import eu.biketrack.android.root.App;
import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;


public class AutoLogin extends Activity implements AutoLoginMVP.View {
    private static final String TAG = "AutoLogin";
    @Inject
    public Session session;
    @Inject
    public LoginManagerModule loginManagerModule;
    @Inject
    AutoLoginMVP.Presenter presenter;
    @BindView(R.id.progressBar_autologin)
    ProgressBar pg_bar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_auto_login);
        ButterKnife.bind(this);
//        Log.d(TAG, "oncreate");
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
//
//        loginManagerModule = LoginManagerModule.getInstance();
//        loginManagerModule.init(this.getContext());
//        session = Session.getInstance();

        ((App) getApplication()).getComponent().inject(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.setSession(session);
        presenter.setLoginManager(loginManagerModule);
        presenter.tryConnection();
    }

    //
//    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_auto_login, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        if (pg_bar != null)
//            pg_bar.setVisibility(View.VISIBLE);
//        Log.d(TAG, "oncreate view");
//        return layout;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        Log.d(TAG, "onviewcreated");
//        if (loginManagerModule.getToken() != null && loginManagerModule.getUserId() != null){
//            Log.d(TAG, "getuser");
//            getUser(loginManagerModule.getUserId(), loginManagerModule.getToken());
//        } else {
//            Log.d(TAG, "ask for login");
//            askForLogin();
//        }
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        Log.d(TAG, "ondestroyview");
//        unbinder.unbind();
//    }
//
//    private void getUser(String userId, String token) {
//        Log.d(TAG, "getUser");
//        _disposables.add(
//                biketrackService.getUser(Statics.TOKEN_API, token, userId)
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceptUser>() {
//
//                            @Override
//                            public void onComplete() {Log.d(TAG, "onComplete");}
//
//                            @Override
//                            public void onError(Throwable e) {
//                                loginManagerModule.clear();
//                                askForLogin();}
//
//                            @Override
//                            public void onNext(ReceptUser receptUser) {
//                                Log.d(TAG, receptUser.toString());
//                                if (receptUser.getSuccess()){
//                                    fillSession(userId, token);
//                                    openBikes();
//                                } else {
//                                    loginManagerModule.clear();
//                                    askForLogin();
//                                }
//                            }
//                        })
//        );
//    }
//
//    public void fillSession(String userId, String token){
//        Log.d(TAG, "fillSession");
//        session.setToken(token);
//        session.setUserId(userId);
//    }
//
//    private void askForLogin(){
//        Log.d(TAG, "askForLogin");
//        if (pg_bar != null)
//            pg_bar.setVisibility(View.INVISIBLE);
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new Login(), this.toString())
//                .commit();
//        if (pg_bar != null)
//            pg_bar.setVisibility(View.VISIBLE);
//        Log.d(TAG, loginManagerModule.getUserId() + "  //  " +  loginManagerModule.getToken());
//    }


    @Override
    public void displayProgressBar(boolean display) {
        try {
            if (display)
                pg_bar.setVisibility(View.VISIBLE);
            else
                pg_bar.setVisibility(View.GONE);
        } catch (Exception e) {
            Log.e(TAG, "displayProgressBar: ", e);
        }
    }

    @Override
    public void openLogin() {
        Log.d(TAG, "openLogin: ");
//        getActivity().getSupportFragmentManager().beginTransaction()
//                .replace(android.R.id.content, new Login(), this.toString())
//                .commit();
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void openBikes() {
        Log.d(TAG, "openBikes: ");
        Intent mainactivity_intent = new Intent(this, Bikes.class);
        startActivity(mainactivity_intent);
        this.finish();
    }
}
