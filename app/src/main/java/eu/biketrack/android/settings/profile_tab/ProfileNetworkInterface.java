package eu.biketrack.android.settings.profile_tab;

import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;

/**
 * Created by 42900 on 20/11/2017 for BikeTrack_Android.
 */

public interface ProfileNetworkInterface {
    Observable<ReceptUser> getUser(String userId, String token);
    Observable<ReceptDeleteUser> deleteUser(String userId, String token);
}
