package eu.biketrack.android.bikes;


import android.support.v4.util.Pair;

import java.util.ArrayList;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;

/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public class BikesPresenter implements BikesMVP.Presenter {
    private static final String TAG = "BikesPresenter";
    private BikesMVP.View view;
    private BikesMVP.Model model;

    public BikesPresenter(BikesMVP.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(BikesMVP.View view) {
        this.view = view;
    }

    @Override
    public void getBikes() {
        view.setProgressBar(true);
        model.getBikes();
    }

    @Override
    public void viewUpdate() {
        view.displayBikes();
        view.setProgressBar(false);
    }

}
