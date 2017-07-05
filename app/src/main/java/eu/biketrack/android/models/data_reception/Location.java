package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Location {

    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("coordinates")
    @Expose
    private List<Double> coordinates = null;
    @SerializedName("snr")
    @Expose
    private String snr;
    @SerializedName("station")
    @Expose
    private String station;
    @SerializedName("data")
    @Expose
    private String data;
    @SerializedName("avgSnr")
    @Expose
    private String avgSnr;
    @SerializedName("rssi")
    @Expose
    private String rssi;
    @SerializedName("seqNumber")
    @Expose
    private String seqNumber;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Double> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Double> coordinates) {
        this.coordinates = coordinates;
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

    public String getAvgSnr() {
        return avgSnr;
    }

    public void setAvgSnr(String avgSnr) {
        this.avgSnr = avgSnr;
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
        return "Location{" +
                "timestamp='" + timestamp + '\'' +
                ", id='" + id + '\'' +
                ", coordinates=" + coordinates +
                ", snr='" + snr + '\'' +
                ", station='" + station + '\'' +
                ", data='" + data + '\'' +
                ", avgSnr='" + avgSnr + '\'' +
                ", rssi='" + rssi + '\'' +
                ", seqNumber='" + seqNumber + '\'' +
                '}';
    }
}