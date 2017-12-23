package eu.biketrack.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;

    @SerializedName("__v")
    @Expose
    private Integer v;

    @SerializedName("bikes")
    @Expose
    private List<String> bikes = null;

    @SerializedName("updated")
    @Expose
    private String updated;

    @SerializedName("created")
    @Expose
    private String created;

    @SerializedName("dob")
    @Expose
    private String dob;

    @SerializedName("facebook")
    @Expose
    private String facebook;

    @SerializedName("google")
    @Expose
    private String google;


    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<String> getBikes() {
        return bikes;
    }

    public void setBikes(List<String> bikes) {
        this.bikes = bikes;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", v=" + v +
                ", bikes=" + bikes +
                ", updated='" + updated + '\'' +
                ", created='" + created + '\'' +
                ", dob='" + dob + '\'' +
                ", facebook='" + facebook + '\'' +
                ", google='" + google + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}