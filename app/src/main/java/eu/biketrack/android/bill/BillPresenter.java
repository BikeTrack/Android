package eu.biketrack.android.bill;

import android.graphics.Bitmap;

import eu.biketrack.android.models.biketracker.BikeTrackerList;

/**
 * Created by 42900 on 29/12/2017 for BikeTrack_Android.
 */

public class BillPresenter implements BillMVP.Presenter {
    private BillMVP.Model model;
    private BillMVP.View view;

    public BillPresenter(BillMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(BillMVP.View view) {
        this.view = view;
    }

    @Override
    public void loadBill(String bikeId) {
        view.progressBarVisibility(true);
        BikeTrackerList b = BikeTrackerList.getInstance();
        b.setBikeTrackerListListener(new BikeTrackerList.BikeTrackerListListener() {
            @Override
            public void listUpdated() {

            }

            @Override
            public void bikeCreated() {
            }

            @Override
            public void updatePicture(Bitmap pict) {
                if (pict != null)
                    view.setBitmap(pict);
                view.progressBarVisibility(false);
            }
        });
        model.loadBill(bikeId);
    }
}
