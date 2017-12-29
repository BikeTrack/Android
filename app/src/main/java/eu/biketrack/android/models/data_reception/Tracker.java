package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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
    private List<Choc> choc = null;
    @SerializedName("battery")
    @Expose
    private List<Battery> battery = null;
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

    public List<Choc> getChoc() {
        return choc;
    }

    public void setChoc(List<Choc> choc) {
        this.choc = choc;
    }

    public List<Battery> getBattery() {
        return battery;
    }

    public void setBattery(List<Battery> battery) {
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

    @Override
    public String toString() {
        return "Tracker{" +
                "id='" + id + '\'' +
                ", v=" + v +
                ", locations=" + locations +
                ", choc=" + choc +
                ", battery=" + battery +
                ", updated='" + updated + '\'' +
                ", created='" + created + '\'' +
                '}';
    }

    public int getCurrentRoundedBatteryPercentage() {
        return battery.get(battery.size() - 1).getPourcentage().intValue();
    }

    public void copy(Tracker tracker) {
        id = tracker.getId();
        v = tracker.getV();
        locations = tracker.getLocations();
        choc = tracker.getChoc();
        battery = tracker.getBattery();
        updated = tracker.getUpdated();
        created = tracker.getCreated();
    }
}