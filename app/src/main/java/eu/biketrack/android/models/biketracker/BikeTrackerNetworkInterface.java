package eu.biketrack.android.models.biketracker;

import android.widget.ImageView;

import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;

/**
 * Created by 42900 on 27/10/2017 for BikeTrack_Android.
 */

public interface BikeTrackerNetworkInterface {
    void updateBikeList();

    void createBike(SendBikeInfo bike);

    void updateBike(String bikeId, SendBikeInfo bike);

    void deleteBike(String bikeId, SendBikeInfo bike);

    void displayImage(String token, String id, ImageView imageView);

    void uploadBikePicture(String picture, String bikeId);

    void uploadBikeBill(String picture, String bikeId);

    void downloadBikeBill(String bikeId);

    Observable<ReceiveBike> getBike(String bike, String token);

    Observable<ReceiveTracker> getTracker(String tracker, String token);

    LoginManagerModule getLoginManagerModule();
}
