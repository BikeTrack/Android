package eu.biketrack.android.models.data_send;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.nio.Buffer;

/**
 * Created by 42900 on 04/07/2017 for BikeTrack_Android.
 */

public class Img {
    @SerializedName("buffer")
    @Expose
    private Buffer buffer;

    @SerializedName("contentType")
    @Expose
    private String contentType;

    public Img(Buffer buffer, String contentType) {
        this.buffer = buffer;
        this.contentType = contentType;
    }

    public Buffer getBuffer() {
        return buffer;
    }

    public void setBuffer(Buffer buffer) {
        this.buffer = buffer;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "Img{" +
                "buffer=" + buffer +
                ", contentType='" + contentType + '\'' +
                '}';
    }
}
