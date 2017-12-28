package eu.biketrack.android.editbike;

import android.net.Uri;

import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_send.SendBikeInfo;

/**
 * Created by 42900 on 28/10/2017 for BikeTrack_Android.
 */

public class EditBikePresenter implements EditBikeMVP.Presenter {
    private EditBikeMVP.Model model;
    private EditBikeMVP.View view;

    public EditBikePresenter(EditBikeMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(EditBikeMVP.View view) {
        this.view = view;
    }

    @Override
    public void createBike(SendBikeInfo bike) {
        BikeTrackerList b = BikeTrackerList.getInstance();
        b.setBikeTrackerListListener(new BikeTrackerList.BikeTrackerListListener() {
            @Override
            public void listUpdated() {

            }

            @Override
            public void bikeCreated() {
                closeView();
            }
        });
        model.createBike(bike);
    }

    @Override
    public void updateBike(String bikeId, SendBikeInfo bike) {
        BikeTrackerList b = BikeTrackerList.getInstance();
        b.setBikeTrackerListListener(new BikeTrackerList.BikeTrackerListListener() {
            @Override
            public void listUpdated() {

            }

            @Override
            public void bikeCreated() {
                closeView();
            }
        });
        model.updateBike(bikeId, bike);
    }

    public void closeView(){
        view.close();
    }

    @Override
    public void deleteBike(String bikeId, SendBikeInfo bike) {
        BikeTrackerList b = BikeTrackerList.getInstance();
        b.setBikeTrackerListListener(new BikeTrackerList.BikeTrackerListListener() {
            @Override
            public void listUpdated() {

            }

            @Override
            public void bikeCreated() {
                closeView();
            }
        });
        model.deleteBike(bikeId, bike);
    }

    @Override
    public void uploadBikePhoto(String uri, String bikeId) {
        model.uploadBikePhoto(uri, bikeId);
    }

    @Override
    public void uploadBikeBill(String uri, String bikeId) {
        model.uploadBikeBill(uri, bikeId);
    }
}
