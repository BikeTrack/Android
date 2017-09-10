package eu.biketrack.android.session;

import javax.inject.Singleton;

/**
 * Created by 42900 on 21/06/2017 for BikeTrack_Android.
 */

@Singleton
public class Session {

    private String userId;
    private String token;

    public Session() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void clear(){
        this.userId = null;
        this.token = null;
    }
}
