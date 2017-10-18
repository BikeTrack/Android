package eu.biketrack.beta.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 04/12/2016 for BikeTrack_Android.
 */

public class SendBikeInfo {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("brand")
    @Expose
    private String brand;

    @SerializedName("tracker")
    @Expose
    private String tracker;

    public SendBikeInfo(String name, String brand, String tracker) {
        this.name = name;
        this.brand = brand;
        this.tracker = tracker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTracker() {
        return tracker;
    }

    public void setTracker(String tracker) {
        this.tracker = tracker;
    }

    @Override
    public String toString() {
        return "SendBikeInfo{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", tracker='" + tracker + '\'' +
                '}';
    }
}