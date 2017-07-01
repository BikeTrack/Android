package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.session.LoginManager;
import eu.biketrack.android.session.Session;
import eu.biketrack.android.activities.MainActivity;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.data_reception.ReceptUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class AutoLoginFragment extends Fragment {
    private static String TAG = "BIKETRACK - AutoLog";
    private LoginManager loginManager;
    private Session session;
    private Unbinder unbinder;

    @BindView(R.id.progressBar_autologin)
    ProgressBar pg_bar;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "oncreate");
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();

        loginManager = LoginManager.getInstance();
        loginManager.init(this.getContext());
        session = Session.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_auto_login, container, false);
        unbinder = ButterKnife.bind(this, layout);
        pg_bar.setVisibility(View.VISIBLE);
        Log.d(TAG, "oncreate view");
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onviewcreated");
        if (loginManager.getToken() != null && loginManager.getUserId() != null){
            Log.d(TAG, "getuser");
            getUser(loginManager.getUserId(), loginManager.getToken());
        } else {
            Log.d(TAG, "ask for login");
            askForLogin();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "ondestroyview");
        unbinder.unbind();
    }

    private void getUser(String userId, String token) {
        Log.d(TAG, "getUser");
        _disposables.add(
                biketrackService.getUser(Statics.TOKEN_API, token, userId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ReceptUser>() {

                            @Override
                            public void onComplete() {Log.d(TAG, "onComplete");}

                            @Override
                            public void onError(Throwable e) {
                                loginManager.clear();
                                askForLogin();}

                            @Override
                            public void onNext(ReceptUser receptUser) {
                                Log.d(TAG, receptUser.toString());
                                if (receptUser.getSuccess()){
                                    fillSession(userId, token);
                                    openBikes();
                                } else {
                                    loginManager.clear();
                                    askForLogin();
                                }
                            }
                        })
        );
    }

    public void fillSession(String userId, String token){
        Log.d(TAG, "fillSession");
        session.setToken(token);
        session.setUserId(userId);
    }

    private void askForLogin(){
        Log.d(TAG, "askForLogin");
        pg_bar.setVisibility(View.INVISIBLE);
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(android.R.id.content, new LoginFragment(), this.toString())
                .commit();
        pg_bar.setVisibility(View.VISIBLE);
        Log.d(TAG, loginManager.getUserId() + "  //  " +  loginManager.getToken());
    }

    private void openBikes(){
        Log.d(TAG, "openBikes");
        pg_bar.setVisibility(View.INVISIBLE);
        Intent mainactivity_intent = new Intent(getActivity(), MainActivity.class);
        startActivity(mainactivity_intent);
        getActivity().finish();
    }
}
