package eu.biketrack.android.bikes;

import android.util.Pair;

import java.util.ArrayList;

import eu.biketrack.android.login.LoginMVP;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public interface BikesMVP {
    interface View{
        void displayBikes(ArrayList<Pair<Bike, Tracker>> bikeArrayList);
    }

    interface Presenter {
        void setView(BikesMVP.View view);
        void viewUpdate();
    }

    interface Model {
        void setPresenter(BikesMVP.Presenter presenter);
        Throwable getError();

    }
}
