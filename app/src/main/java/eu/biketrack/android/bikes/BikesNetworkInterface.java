package eu.biketrack.android.bikes;

import android.support.v4.util.Pair;

import java.util.ArrayList;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.Tracker;
import rx.Observable;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public interface BikesNetworkInterface {
    ArrayList<Pair<Bike, Tracker>> getBikeArrayList(String user, String token);
    Observable<ReceptUser> getUser(String userId, String token);
    Observable<ReceiveBike> getBike(String bike, String token);
    Observable<ReceiveTracker> getTracker(String tracker, String token);
}
