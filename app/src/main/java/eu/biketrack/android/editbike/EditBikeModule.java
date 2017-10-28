package eu.biketrack.android.editbike;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.models.biketracker.BikeTrackerNetworkInterface;
import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 28/10/2017 for BikeTrack_Android.
 */
@Module
public class EditBikeModule {
    @Provides
    public EditBikeMVP.Presenter provideBikesPresenter(EditBikeMVP.Model model){
        return new EditBikePresenter(model);
    }

    @Provides
    public EditBikeMVP.Model provideBikesModel(LoginManagerModule loginManagerModule,
                                            BikeTrackerNetworkInterface bikeTrackerNetworkInterface){
        return new EditBikeModel(loginManagerModule, bikeTrackerNetworkInterface);
    }


}
