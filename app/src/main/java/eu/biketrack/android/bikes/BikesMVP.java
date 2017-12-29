package eu.biketrack.android.bikes;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public interface BikesMVP {
    interface View {
        void setProgressBar(boolean visible);

        void displayBikes();
    }

    interface Presenter {
        void setView(BikesMVP.View view);

        void getBikes();

        void clearBikes();

        void viewUpdate();
    }

    interface Model {
        void setPresenter(BikesMVP.Presenter presenter);

        void getBikes();

        //        void setBikeArrayList(ArrayList<android.support.v4.util.Pair<Bike, Tracker>> list);
        Throwable getError();

        void updateDone();

        void clearBikes();
    }
}
