package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 20/06/2017 for BikeTrack_Android.
 */

public class SendBikeUpdate {
    @SerializedName("userId")
    @Expose
    private String userId;

    @SerializedName("bikeId")
    @Expose
    private String bikeId;

    @SerializedName("update")
    @Expose
    private SendBikeInfo bikeInfo;

    public SendBikeUpdate(String userId, String bikeId, SendBikeInfo bikeInfo) {
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
        return "SendBikeUpdate{" +
                "userId='" + userId + '\'' +
                ", bikeId='" + bikeId + '\'' +
                ", bikeInfo=" + bikeInfo +
                '}';
    }
}
