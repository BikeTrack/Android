package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 02/03/2017 for BikeTrack_Android.
 */


public class AuthenticateReception {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "success=" + success +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}