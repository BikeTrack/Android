package eu.biketrack.android.bikes;

import android.support.v4.util.Pair;

import java.util.ArrayList;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public interface BikesMVP {
    interface View{
        void setProgressBar(boolean visible);
        void displayBikes(ArrayList<Pair<Bike, Tracker>> bikeArrayList);
    }

    interface Presenter {
        void setView(BikesMVP.View view);
        void getBikes();
        void viewUpdate(ArrayList<android.support.v4.util.Pair<Bike, Tracker>> list);
    }

    interface Model {
        void setPresenter(BikesMVP.Presenter presenter);
        void getBikes();
        void setBikeArrayList(ArrayList<android.support.v4.util.Pair<Bike, Tracker>> list);
        Throwable getError();
    }
}
