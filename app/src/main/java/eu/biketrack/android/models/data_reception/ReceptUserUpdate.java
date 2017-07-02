package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.android.models.User;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class ReceptUserUpdate {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("message")
    @Expose
    private String message;

    public ReceptUserUpdate(Boolean success, User user, String message) {
        this.success = success;
        this.user = user;
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReceptUserUpdate{" +
                "success=" + success +
                ", user=" + user +
                ", message='" + message + '\'' +
                '}';
    }
}
