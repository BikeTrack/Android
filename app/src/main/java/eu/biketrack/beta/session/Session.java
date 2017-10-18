package eu.biketrack.beta.session;

/**
 * Created by 42900 on 21/06/2017 for BikeTrack_Android.
 */

public class Session {
    private static final Session ourInstance = new Session();

    public static Session getInstance() {
        return ourInstance;
    }

    private String userId;
    private String token;

    private Session() {
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
