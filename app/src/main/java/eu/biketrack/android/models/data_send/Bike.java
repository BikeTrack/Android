package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 04/12/2016 for BikeTrack_Android.
 */

public class Bike {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("longitude")
    @Expose
    private Integer longitude;
    @SerializedName("latitude")
    @Expose
    private Integer latitude;

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The longitude
     */
    public Integer getLongitude() {
        return longitude;
    }

    /**
     *
     * @param longitude
     * The longitude
     */
    public void setLongitude(Integer longitude) {
        this.longitude = longitude;
    }

    /**
     *
     * @return
     * The latitude
     */
    public Integer getLatitude() {
        return latitude;
    }

    /**
     *
     * @param latitude
     * The latitude
     */
    public void setLatitude(Integer latitude) {
        this.latitude = latitude;
    }

}