package eu.biketrack.beta.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by 42900 on 20/06/2017 for BikeTrack_Android.
 */

public class SigfoxData {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("snr")
    @Expose
    private String snr;
    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("altitude")
    @Expose
    private String altitude;
    @SerializedName("rssi")
    @Expose
    private String rssi;
    @SerializedName("seqNumber")
    @Expose
    private String seqNumber;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSnr() {
        return snr;
    }

    public void setSnr(String snr) {
        this.snr = snr;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getRssi() {
        return rssi;
    }

    public void setRssi(String rssi) {
        this.rssi = rssi;
    }

    public String getSeqNumber() {
        return seqNumber;
    }

    public void setSeqNumber(String seqNumber) {
        this.seqNumber = seqNumber;
    }

    @Override
    public String toString() {
        return "SigfoxData{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", snr='" + snr + '\'' +
                ", station='" + station + '\'' +
                ", data='" + data + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", altitude='" + altitude + '\'' +
                ", rssi='" + rssi + '\'' +
                ", seqNumber='" + seqNumber + '\'' +
                '}';
    }
}
