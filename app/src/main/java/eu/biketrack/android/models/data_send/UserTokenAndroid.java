package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 06/01/2018 for BikeTrack_Android.
 */

public class UserTokenAndroid {
    @SerializedName("notifToken")
    @Expose
    private String notifToken;

    public UserTokenAndroid(String notifToken) {
        this.notifToken = notifToken;
    }

    public String getNotifToken() {
        return notifToken;
    }

    public void setNotifToken(String notifToken) {
        this.notifToken = notifToken;
    }

    @Override
    public String toString() {
        return "{" +
                "notifToken='" + notifToken + '\'' +
                '}';
    }
}
