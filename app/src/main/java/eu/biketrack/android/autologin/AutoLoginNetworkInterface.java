package eu.biketrack.android.autologin;


import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public interface AutoLoginNetworkInterface {

    Observable<ReceptUser> getUser(String userId, String token);
}
