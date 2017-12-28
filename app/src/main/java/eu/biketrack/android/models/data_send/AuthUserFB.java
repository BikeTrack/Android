package eu.biketrack.android.models.data_send;

/**
 * Created by 42900 on 28/12/2017 for BikeTrack_Android.
 */

//fbToken
public class AuthUserFB {
    private String fbToken;

    public AuthUserFB(String fbToken) {
        this.fbToken = fbToken;
    }

    public String getFbToken() {
        return fbToken;
    }

    public void setFbToken(String fbToken) {
        this.fbToken = fbToken;
    }

    @Override
    public String toString() {
        return "AuthUserFB{" +
                "fbToken='" + fbToken + '\'' +
                '}';
    }
}
