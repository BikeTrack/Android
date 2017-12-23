package eu.biketrack.android.settings.profile_tab;


/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

public class ProfilePresenter implements ProfileMVP.Presenter{

    private ProfileMVP.Model model;
    private ProfileMVP.View view;

    public ProfilePresenter(ProfileMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ProfileMVP.View view) {
        this.view = view;
    }

    public void getUserData(){
        model.getUserData().doOnNext(receptUser -> {
            view.set_email(receptUser.getUser().getEmail());
            view.set_lastname(receptUser.getUser().getLastname());
            view.set_firstname(receptUser.getUser().getName());
        }).subscribe();
    }

    @Override
    public void logoff() {
        model.logoff();
    }
}
