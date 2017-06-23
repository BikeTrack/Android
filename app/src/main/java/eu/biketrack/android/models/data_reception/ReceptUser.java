package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.android.models.User;

public class ReceptUser {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("user")
    @Expose
    private User user;
    @SerializedName("message")
    @Expose
    private Message message;

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

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReceptUser{" +
                "success=" + success +
                ", user=" + user +
                ", message=" + message +
                '}';
    }
}