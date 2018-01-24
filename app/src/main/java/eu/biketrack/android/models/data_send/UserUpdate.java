package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.android.models.User;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class UserUpdate {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("alertMail")
    @Expose
    private String alertMail;


    public UserUpdate(User user) {
        this.email = user.getEmail();
        this.name = user.getName();
        this.lastname = user.getLastname();
        this.dob = user.getDob();
        this.alertMail = user.getAlertMail();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getAlertMail() {
        return alertMail;
    }

    public void setAlertMail(String alertMail) {
        this.alertMail = alertMail;
    }

    @Override
    public String toString() {
        return "UserUpdate{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", dob='" + dob + '\'' +
                ", alertMail='" + alertMail + '\'' +
                '}';
    }
}
