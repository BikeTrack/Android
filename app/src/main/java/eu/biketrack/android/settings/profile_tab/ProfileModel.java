package eu.biketrack.android.settings.profile_tab;

import eu.biketrack.android.session.LoginManagerModule;

/**
 * Created by 42900 on 20/11/2017 for BikeTrack_Android.
 */

public class ProfileModel implements ProfileMVP.Model {
    private ProfileNetworkInterface profileNetworkInterface;
    private LoginManagerModule loginManagerModule;

    public ProfileModel(ProfileNetworkInterface profileNetworkInterface, LoginManagerModule loginManagerModule) {
        this.profileNetworkInterface = profileNetworkInterface;
        this.loginManagerModule = loginManagerModule;
    }

    @Override
    public void getUserData() {

    }

    @Override
    public void logoff() {
        loginManagerModule.clear();

    }
}
