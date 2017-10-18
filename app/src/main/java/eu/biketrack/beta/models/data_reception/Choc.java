package eu.biketrack.beta.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Choc {

    @SerializedName("checked")
    @Expose
    private Boolean checked;
    @SerializedName("_id")
    @Expose
    private String id;
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

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        return "Choc{" +
                "checked=" + checked +
                ", id='" + id + '\'' +
                ", snr='" + snr + '\'' +
                ", station='" + station + '\'' +
                ", data='" + data + '\'' +
                ", avgSnr='" + avgSnr + '\'' +
                ", rssi='" + rssi + '\'' +
                ", seqNumber='" + seqNumber + '\'' +
                '}';
    }
}