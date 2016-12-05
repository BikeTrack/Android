package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Vector;

/**
 * Created by 42900 on 04/12/2016 for BikeTrack_Android.
 */

public class Bike implements Serializable{

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("long")
    @Expose
    private Integer _long;
    @SerializedName("lat")
    @Expose
    private Integer lat;
    @SerializedName("_id")
    @Expose
    private String id;

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
    public Integer getLong() {
        return _long;
    }

    /**
     *
     * @param _long
     * The long
     */
    public void setLong(Integer _long) {
        this._long = _long;
    }

    /**
     *
     * @return
     * The lat
     */
    public Integer getLat() {
        return lat;
    }

    /**
     *
     * @param lat
     * The lat
     */
    public void setLat(Integer lat) {
        this.lat = lat;
    }

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The _id
     */
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Bike{" +
                "name='" + name + '\'' +
                ", _long=" + _long +
                ", lat=" + lat +
                ", id='" + id + '\'' +
                '}';
    }
}