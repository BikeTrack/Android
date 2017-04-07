package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 02/03/2017 for BikeTrack_Android.
 */

public class SignupReception {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "SignupReception{" +
                "success=" + success +
                ", message='" + message + '\'' +
                '}';
    }
}