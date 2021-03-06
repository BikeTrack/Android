package eu.biketrack.android.autologin;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public class AutoLoginNetwork implements AutoLoginNetworkInterface{
    private static final String TAG = "AutoLoginNetwork";
    private BiketrackService biketrackService;

    public AutoLoginNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public Observable<ReceptUser> getUser(String userId, String token) {
        return biketrackService.getUser(token, userId);
    }
}
