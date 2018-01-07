package eu.biketrack.android.login;

import android.app.Activity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_send.SendUserTokenAndroid;
import eu.biketrack.android.models.data_send.UserTokenAndroid;
import eu.biketrack.android.session.LoginManagerModule;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public class LoginPresenter implements LoginMVP.Presenter {
    private static final String TAG = "LoginPresenter";
    private LoginMVP.View view;
    private LoginMVP.Model model;
    private boolean connectionByFacebook = false;
    private String fbemail;
    private String fbtoken;

    public LoginPresenter(LoginMVP.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(LoginMVP.View view) {
        this.view = view;
    }

    @Override
    public void connexionButtonClicked() {
        model.connection(view.getUserEmail(), view.getUserPassword(), view.getRememberMe());
        view.loading(true);
    }

    @Override
    public void viewAfterConnection() {
        Log.d(TAG, "viewAfterConnection: ");
        if (model.getError() == null) {
            view.close();
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
        }
        else {
            if (model.getError().getMessage().equals("HTTP 401 Unauthorized") && connectionByFacebook) {
                model.subscriptionByFacebook(fbemail, fbtoken);
                connectionByFacebook = false;
            }
            view.loading(false);
        }
    }

    @Override
    public void goToSubscribe() {
        view.openSubscribe();
    }

    @Override
    public void facebookClicked(String email, String token) {
        connectionByFacebook = true;
        model.connection(email, token, true);
        fbemail = email;
        fbtoken = token;
    }
}
