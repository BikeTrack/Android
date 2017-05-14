package eu.biketrack.android.models.data_reception;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by 42900 on 02/03/2017 for BikeTrack_Android.
 */


public class AuthenticateReception implements Parcelable{

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("message")
    @Expose
    private String message;

    public static final Parcelable.Creator<AuthenticateReception> CREATOR = new Parcelable.Creator<AuthenticateReception>() {
        public AuthenticateReception createFromParcel(Parcel in) {
            return new AuthenticateReception(in);
        }

        public AuthenticateReception[] newArray(int size) {
            return new AuthenticateReception[size];
        }
    };

    private AuthenticateReception(Parcel in){
        token = in.readString();
        userId = in.readString();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "UserToken{" +
                "success=" + success +
                ", token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(userId);
    }
}