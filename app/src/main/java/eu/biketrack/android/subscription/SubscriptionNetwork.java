package eu.biketrack.android.subscription;

import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import rx.Observable;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

public class SubscriptionNetwork implements SubscriptionNetworkInterface{

    private BiketrackService biketrackService;

    public SubscriptionNetwork(BiketrackService biketrackService) {
        this.biketrackService = biketrackService;
    }

    @Override
    public Observable<SignupReception> signup(AuthUser authUser) {
        return biketrackService.createUser(authUser);
    }

    @Override
    public Observable<AuthenticateReception> connection(AuthUser authUser) {
        return biketrackService.connectUser(authUser);
    }
}
