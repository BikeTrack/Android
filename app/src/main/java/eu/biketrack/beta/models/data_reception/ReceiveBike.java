package eu.biketrack.beta.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReceiveBike {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("bike")
    @Expose
    private Bike bike;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    @Override
    public String toString() {
        return "ReceiveBike{" +
                "success=" + success +
                ", bike=" + bike +
                '}';
    }
}