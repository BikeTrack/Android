package eu.biketrack.android.notifications;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.gson.Gson;

import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_send.SendUserTokenAndroid;
import eu.biketrack.android.models.data_send.UserTokenAndroid;
import eu.biketrack.android.session.LoginManagerModule;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);

    }

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        try {
            BiketrackService biketrackService = ApiConnectModule.provideApiService();
            LoginManagerModule loginManagerModule = new LoginManagerModule(getApplication().getApplicationContext());
            SendUserTokenAndroid sendUserTokenAndroid = new SendUserTokenAndroid(loginManagerModule.getUserId(), new UserTokenAndroid(token));
            Gson gson = new Gson();
            Log.d(TAG, "sendRegistrationToServer: " + gson.toJson(sendUserTokenAndroid));
            biketrackService.updateUserToken(loginManagerModule.getToken(), sendUserTokenAndroid)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(receptUserUpdate -> {
                                Log.d(TAG, "sendRegistrationToServer: token updated");
                            },
                            throwable -> {
                                Log.e(TAG, "sendRegistrationToServer: ", throwable);
                            },
                            () -> {

                            });
        } catch (Exception e) {
            Log.e(TAG, "sendRegistrationToServer: ", e);
        }
    }
}
