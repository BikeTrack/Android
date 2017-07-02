package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.android.models.User;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class UserUpdate {
    @SerializedName("mail")
    @Expose
    private String mail;

    public UserUpdate(User user) {
        this.mail = user.getMail();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
