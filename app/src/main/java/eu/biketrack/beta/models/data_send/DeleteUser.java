package eu.biketrack.beta.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class DeleteUser {
    @SerializedName("userId")
    @Expose
    private String userId;

    public DeleteUser(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "DeleteUser{" +
                "userId='" + userId + '\'' +
                '}';
    }
}
