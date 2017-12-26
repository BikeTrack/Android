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

    @Override
    public void clearBikes() {
        model.clearBikes();
    }
}
