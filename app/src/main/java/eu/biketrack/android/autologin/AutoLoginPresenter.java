package eu.biketrack.android.autologin;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_send.SendUserTokenAndroid;
import eu.biketrack.android.models.data_send.UserTokenAndroid;
import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginPresenter implements AutoLoginMVP.Presenter {
    private static final String TAG = "AutoLoginPresenter";
    private AutoLoginMVP.View view;
    private AutoLoginMVP.Model model;
    private Session session;
    private LoginManagerModule loginManagerModule;

    public AutoLoginPresenter(AutoLoginMVP.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(AutoLoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setLoginManager(LoginManagerModule loginManagerModule) {
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void tryConnection() {
        view.displayProgressBar(true);
        if (loginManagerModule.getEmail() != null && loginManagerModule.getPassword() != null) {
            model.connect();
        } else {
            view.displayProgressBar(false);
            view.openLogin();
        }
        view.displayProgressBar(false);
    }

    @Override
    public void viewAfterGettingUser() {
        view.displayProgressBar(true);
        if (model.getError() == null) {
            String refreshedToken = FirebaseInstanceId.getInstance().getToken();
            if (refreshedToken != null) {
                try {
                    BiketrackService biketrackService = ApiConnectModule.provideApiService();
                    LoginManagerModule loginManagerModule = new LoginManagerModule(((Activity) view).getApplicationContext());
                    SendUserTokenAndroid sendUserTokenAndroid = new SendUserTokenAndroid(loginManagerModule.getUserId(), new UserTokenAndroid(refreshedToken));
                    Gson gson = new Gson();
                    Log.d(TAG, "viewAfterConnection: " + gson.toJson(sendUserTokenAndroid));
                    biketrackService.updateUserToken(loginManagerModule.getToken(), sendUserTokenAndroid)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(receptUserUpdate -> {
                                        Log.d(TAG, "viewAfterConnection: token updated");
                                    },
                                    throwable -> {
                                        Log.e(TAG, "viewAfterConnection: ", throwable);
                                    },
                                    () -> {

                                    });
                } catch (Exception e) {
                    Log.e(TAG, "viewAfterConnection: ", e);
                }
            }
            view.displayProgressBar(false);
            view.openBikes();
        } else {
            loginManagerModule.clear();
            view.displayProgressBar(false);
            view.openLogin();
        }
    }
}
