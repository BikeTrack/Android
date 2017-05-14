package eu.biketrack.android.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("mail")
    @Expose
    private String mail;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", mail='" + mail + '\'' +
                ", v=" + v +
                ", bikes=" + bikes +
                ", updated='" + updated + '\'' +
                ", created='" + created + '\'' +
                '}';
    }
}