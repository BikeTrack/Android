package eu.biketrack.android.settings.profile_tab;

import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import rx.Observable;

/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

public interface ProfileMVP {
    interface View {
        void set_email(String _email);
        void set_lastname(String _lastname);
        void set_firstname(String _firstname);
        void goToLoginView();
    }

    interface Presenter {
        void setView(ProfileMVP.View view);
        void getUserData();
        void logoff();
        void deleteAccount();
    }

    interface Model {
        Observable<ReceptUser> getUserData();
        void logoff();
        Observable<ReceptDeleteUser> deleteAccount();
    }
}
