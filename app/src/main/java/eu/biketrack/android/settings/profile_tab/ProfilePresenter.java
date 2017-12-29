package eu.biketrack.android.settings.profile_tab;


import android.content.res.Resources;

import eu.biketrack.android.R;
import eu.biketrack.android.models.User;

/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

public class ProfilePresenter implements ProfileMVP.Presenter {
    private static final String TAG = "ProfilePresenter";
    private Resources res;
    private User currentUser;
    private ProfileMVP.Model model;
    private ProfileMVP.View view;

    public ProfilePresenter(ProfileMVP.Model model) {
        this.model = model;
    }

    @Override
    public void setView(ProfileMVP.View view) {
        this.view = view;
    }

    @Override
    public void setResources(Resources ressources) {
        res = ressources;
    }

    public void getUserData() {
        model.getUserData().doOnNext(receptUser -> {
            currentUser = receptUser.getUser();
            view.set_email(receptUser.getUser().getEmail());
            view.set_lastname(receptUser.getUser().getLastname());
            view.set_firstname(receptUser.getUser().getName());
            view.set_dob(receptUser.getUser().getDob());
        }).subscribe();
    }

    @Override
    public void logoff() {
        model.logoff();
        view.close();
    }

    @Override
    public void deleteAccount() {
        model.deleteAccount()
                .doOnNext(receptDeleteUser -> {
                    logoff();
                    view.close();
                })
                .subscribe();
    }

    @Override
    public void saveUserData() {
        User tmp = currentUser;
        if (view.get_email().isEmpty()) {
            view.displayMessage(res.getString(R.string.error_check_email));
            return;
        }
        tmp.setEmail(view.get_email());
        tmp.setName(view.get_firstname());
        tmp.setLastname(view.get_lastname());
        tmp.setDob(view.get_dob());
        model.saveUserData(tmp)
                .doOnNext(receptUserUpdate -> {
                    if (receptUserUpdate != null) {
                        view.displayMessage(res.getString(R.string.user_saved));
                        view.close();
                    } else {
                        onError();
                    }
                })
                .subscribe();
    }

    private void onError() {
        view.displayMessage("Error");
        view.set_email(currentUser.getEmail());
        view.set_lastname(currentUser.getLastname());
        view.set_firstname(currentUser.getName());
        view.set_dob(currentUser.getDob());
    }
}
