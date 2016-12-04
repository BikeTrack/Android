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
    @SerializedName("long")
    @Expose
    private String _long;
    @SerializedName("lat")
    @Expose
    private String lat;

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
     * The _long
     */
    public String getLong() {
        return _long;
    }

    /**
     *
     * @param _long
     * The long
     */
    public void setLong(String _long) {
        this._long = _long;
    }

    /**
     *
     * @return
     * The lat
     */
    public String getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "name='" + name + '\'' +
                ", _long='" + _long + '\'' +
                ", lat='" + lat + '\'' +
                '}';
    }
}