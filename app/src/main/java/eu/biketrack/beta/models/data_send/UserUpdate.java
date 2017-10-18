package eu.biketrack.beta.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import eu.biketrack.beta.models.User;

/**
 * Created by 42900 on 02/07/2017 for BikeTrack_Android.
 */

public class UserUpdate {
    @SerializedName("mail")
    @Expose
    private String mail;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lastname")
    @Expose
    private String lastname;

//    @SerializedName("img")
//    @Expose
//    private Img img;

    public UserUpdate(User user) {
        this.mail = user.getMail();
        this.name = user.getName();
        this.lastname = user.getLastname();
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

//    public Img getImg() {
//        return img;
//    }
//
//    public void setImg(Img img) {
//        this.img = img;
//    }
//
//    @Override
//    public String toString() {
//        return "UserUpdate{" +
//                "mail='" + mail + '\'' +
//                ", name='" + name + '\'' +
//                ", lastname='" + lastname + '\'' +
//                ", img=" + img +
//                '}';
//    }
}
