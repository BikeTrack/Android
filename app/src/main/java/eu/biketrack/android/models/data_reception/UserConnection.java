package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class UserConnection implements Serializable{

    @SerializedName("success")
    @Expose
    private boolean success;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     *
     * @return
     * The success
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     *
     * @param success
     * The success
     */
    public void setSuccess(boolean success) {
        this.success = success;
    }

    /**
     *
     * @return
     * The token
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token
     * The token
     */
    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "UserConnection{" +
                "success=" + success +
                ", token='" + token + '\'' +
                '}';
    }
}