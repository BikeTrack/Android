package eu.biketrack.android.models.data_reception;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */

public class UserInscription {

    @SerializedName("sucess")
    @Expose
    private boolean sucess;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     *
     * @return
     * The sucess
     */
    public boolean isSucess() {
        return sucess;
    }

    /**
     *
     * @param sucess
     * The sucess
     */
    public void setSucess(boolean sucess) {
        this.sucess = sucess;
    }

    /**
     *
     * @return
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}