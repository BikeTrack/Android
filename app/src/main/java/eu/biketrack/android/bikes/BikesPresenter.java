package eu.biketrack.android.bikes;


/**
 * Created by 42900 on 28/09/2017 for BikeTrack_Android.
 */

public class BikesPresenter implements BikesMVP.Presenter {
    private static final String TAG = "BikesPresenter";
    private BikesMVP.View view;
    private BikesMVP.Model model;

    public BikesPresenter(BikesMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(BikesMVP.View view) {
        this.view = view;
    }

    @Override
    public void getBikes() {
        model.getBikes();
    }

    @Override
    public void viewUpdate() {

    }
}
