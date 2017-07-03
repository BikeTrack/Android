package eu.biketrack.android.models.data_reception;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tracker {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("__v")
    @Expose
    private Integer v;
    @SerializedName("locations")
    @Expose
    private List<Location> locations = null;
    @SerializedName("choc")
    @Expose
    private List<Object> choc = null;
    @SerializedName("battery")
    @Expose
    private List<Object> battery = null;
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

    public Integer getV() {
        return v;
    }

    public void setV(Integer v) {
        this.v = v;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public List<Object> getChoc() {
        return choc;
    }

    public void setChoc(List<Object> choc) {
        this.choc = choc;
    }

    public List<Object> getBattery() {
        return battery;
    }

    public void setBattery(List<Object> battery) {
        this.battery = battery;
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

}