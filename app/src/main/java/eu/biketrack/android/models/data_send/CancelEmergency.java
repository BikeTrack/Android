package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 09/01/2018 for BikeTrack_Android.
 */

public class CancelEmergency {
    @SerializedName("AlertID")
    @Expose
    private String alertId;

    public CancelEmergency(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    @Override
    public String toString() {
        return "CancelEmergency{" +
                "alertId='" + alertId + '\'' +
                '}';
    }
}
