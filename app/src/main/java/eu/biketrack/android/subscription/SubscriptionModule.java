package eu.biketrack.android.subscription;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

@Module
public class SubscriptionModule {
    @Provides
    public SubscriptionMVP.Presenter provideSubscriptionPresenter(SubscriptionMVP.Model model){
        return new SubscriptionPresenter(model);
    }

    @Provides
    public SubscriptionMVP.Model provideSubscriptionModel(SubscriptionNetworkInterface subscriptionNetworkInterface, LoginManagerModule loginManagerModule){
        return new SubscriptionModel(subscriptionNetworkInterface, loginManagerModule);
    }

    @Provides
    public SubscriptionNetworkInterface provideSubscriptionNetworkInterface(BiketrackService biketrackService){
        return new SubscriptionNetwork(biketrackService);
    }
}
