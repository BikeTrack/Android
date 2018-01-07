package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 06/01/2018 for BikeTrack_Android.
 */

public class SendUserTokenAndroid {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("update")
    @Expose
    private UserTokenAndroid user;

    public SendUserTokenAndroid(String userId, UserTokenAndroid user) {
        this.userId = userId;
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserTokenAndroid getUser() {
        return user;
    }

    public void setUser(UserTokenAndroid user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "{" +
                "userId='" + userId + '\'' +
                ",update=" + user +
                '}';
    }
}
