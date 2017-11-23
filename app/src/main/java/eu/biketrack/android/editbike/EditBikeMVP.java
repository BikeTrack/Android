package eu.biketrack.android.editbike;

import eu.biketrack.android.models.data_send.SendBikeInfo;

/**
 * Created by 42900 on 28/10/2017 for BikeTrack_Android.
 */

public interface EditBikeMVP {
    interface View{
        void close();
    }

    interface Presenter{
        void setView(EditBikeMVP.View view);
        void createBike(SendBikeInfo bike);
        void updateBike(String bikeId, SendBikeInfo bike);
        void deleteBike(String bikeId, SendBikeInfo bike);
    }

    interface Model{
        void createBike(SendBikeInfo bike);
        void updateBike(String bikeId, SendBikeInfo bike);
        void deleteBike(String bikeId, SendBikeInfo bike);
    }
}
