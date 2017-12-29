package eu.biketrack.android.models.biketracker;

import android.graphics.Bitmap;
import android.widget.ImageView;

import eu.biketrack.android.models.data_send.SendBikeInfo;

/**
 * Created by 42900 on 27/10/2017 for BikeTrack_Android.
 */

public interface BikeTrackerNetworkInterface {
    void updateBikeList();
    void createBike(SendBikeInfo bike);
    void updateBike(String bikeId, SendBikeInfo bike);
    void deleteBike(String bikeId, SendBikeInfo bike);
    void displayImage(String url, ImageView imageView);

    void uploadBikePicture(String picture, String bikeId);
    void uploadBikeBill(String picture, String bikeId);

    void downloadBikeBill(String bikeId);
}
