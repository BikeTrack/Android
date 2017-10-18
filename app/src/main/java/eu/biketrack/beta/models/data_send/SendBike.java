package eu.biketrack.beta.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 02/05/2017 for BikeTrack_Android.
 */

public class SendBike {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("bikeId")
    @Expose
    private String bikeId;

    @SerializedName("bikeInfo")
    @Expose
    private SendBikeInfo bikeInfo;

    public SendBike(String userId, String bikeId, SendBikeInfo bikeInfo) {
        this.userId = userId;
        this.bikeId = bikeId;
        this.bikeInfo = bikeInfo;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBikeId() {
        return bikeId;
    }

    public void setBikeId(String bikeId) {
        this.bikeId = bikeId;
    }

    public SendBikeInfo getBikeInfo() {
        return bikeInfo;
    }

    public void setBikeInfo(SendBikeInfo bikeInfo) {
        this.bikeInfo = bikeInfo;
    }

    @Override
    public String toString() {
        return "SendBike{" +
                "userId='" + userId + '\'' +
                ", bikeId='" + bikeId + '\'' +
                ", bikeInfo=" + bikeInfo +
                '}';
    }
}
