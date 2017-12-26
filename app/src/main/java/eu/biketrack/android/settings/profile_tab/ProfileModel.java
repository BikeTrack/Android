package eu.biketrack.android.settings.profile_tab;

import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.session.LoginManagerModule;
import rx.Observable;

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
    public  Observable<ReceptUser> getUserData() {
        return profileNetworkInterface.getUser(loginManagerModule.getUserId(), loginManagerModule.getToken());
    }

    @Override
    public void logoff() {
        loginManagerModule.clear();
    }

    @Override
    public Observable<ReceptDeleteUser> deleteAccount() {
        return profileNetworkInterface.deleteUser(loginManagerModule.getUserId(), loginManagerModule.getToken());
    }
}
