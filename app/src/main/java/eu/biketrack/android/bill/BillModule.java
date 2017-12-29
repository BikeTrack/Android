package eu.biketrack.android.bill;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 29/12/2017 for BikeTrack_Android.
 */
@Module
public class BillModule {
    @Provides
    public BillMVP.Presenter provideBikesPresenter(BillMVP.Model model){
        return new BillPresenter(model);
    }

    @Provides
    public BillMVP.Model provideBikesModel(LoginManagerModule loginManagerModule,
                                               BikeTrackerNetworkInterface bikeTrackerNetworkInterface){
        return new BillModel(loginManagerModule, bikeTrackerNetworkInterface);
    }
}
