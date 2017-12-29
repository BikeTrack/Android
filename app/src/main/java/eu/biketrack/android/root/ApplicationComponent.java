package eu.biketrack.android.root;

import javax.inject.Singleton;

import dagger.Component;
import eu.biketrack.android.activities.BikeTrack;
import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.autologin.AutoLogin;
import eu.biketrack.android.autologin.AutoLoginModule;
import eu.biketrack.android.bikes.Bikes;
import eu.biketrack.android.bikes.BikesModule;
import eu.biketrack.android.bill.Bill;
import eu.biketrack.android.bill.BillModule;
import eu.biketrack.android.editbike.EditBike;
import eu.biketrack.android.editbike.EditBikeModule;
import eu.biketrack.android.initializer.Initializer;
import eu.biketrack.android.initializer.InitializerModule;
import eu.biketrack.android.login.Login;
import eu.biketrack.android.login.LoginModule;
import eu.biketrack.android.models.biketracker.BikeTrackerListModule;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;
import eu.biketrack.android.settings.profile_tab.EditProfileFragment;
import eu.biketrack.android.settings.profile_tab.ProfileFragment;
import eu.biketrack.android.settings.profile_tab.ProfileModule;
import eu.biketrack.android.subscription.Subscription;
import eu.biketrack.android.subscription.SubscriptionModule;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Singleton
@Component(modules = {
        ApplicationModule.class,
        InitializerModule.class,
        AutoLoginModule.class,
        ApiConnectModule.class,
        LoginModule.class,
        SubscriptionModule.class,
        BikesModule.class,
        BikeTrackerListModule.class,
        EditBikeModule.class,
        ProfileModule.class,
        BillModule.class
})
public interface ApplicationComponent {

    void inject(ApiConnectModule target);

    void inject(BikeTrack target);

    void inject(Initializer target);

    void inject(AutoLogin target);

    void inject(Login target);

    void inject(Subscription target);

    void inject(Bikes target);

    void inject(BikeTrackerNetworkInterface bikeTrackerNetworkInterface);

    void inject(EditBike target);

    void inject(ProfileFragment target);

    void inject(EditProfileFragment target);

    void inject(Bill target);


    LoginManagerModule getLoginManagerModule();


    Session getSession();
}
