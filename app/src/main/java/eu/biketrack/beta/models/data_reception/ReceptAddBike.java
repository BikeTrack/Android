package eu.biketrack.beta.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 02/05/2017 for BikeTrack_Android.
 */

public class ReceptAddBike {
    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("bikeId")
    @Expose
    private String bikeId;

    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ReceptAddBike{" +
                "success=" + success +
                ", bikeId='" + bikeId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
