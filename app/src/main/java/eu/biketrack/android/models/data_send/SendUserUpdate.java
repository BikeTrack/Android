package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.android.models.User;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class SendUserUpdate {

    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("update")
    @Expose
    private UserUpdate user;

    public SendUserUpdate(String userId, UserUpdate user) {
        this.userId = userId;
        this.user = user;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public UserUpdate getUser() {
        return user;
    }

    public void setUser(UserUpdate user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "SendUserUpdate{" +
                "userId='" + userId + '\'' +
                ", user=" + user +
                '}';
    }
}
