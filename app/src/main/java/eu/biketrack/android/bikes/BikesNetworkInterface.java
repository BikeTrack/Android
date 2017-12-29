package eu.biketrack.android.bikes;

import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public interface BikesNetworkInterface {
    void setModel(BikesMVP.Model model);

    void getBikeArrayList(String user, String token);

    Observable<ReceptUser> getUser(String userId, String token);

    Observable<ReceiveBike> getBike(String bike, String token);

    Observable<ReceiveTracker> getTracker(String tracker, String token);
}
