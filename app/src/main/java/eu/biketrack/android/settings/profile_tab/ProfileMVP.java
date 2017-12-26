package eu.biketrack.android.settings.profile_tab;

import android.content.res.Resources;

import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.ReceptUserUpdate;
import rx.Observable;

/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

public interface ProfileMVP {
    interface View {
        void set_email(String _email);
        void set_lastname(String _lastname);
        void set_firstname(String _firstname);
        void set_dob(String _dob);
        String get_email();
        String get_lastname();
        String get_firstname();
        String get_dob();
        void close();
        void displayMessage(String message);
    }

    interface Presenter {
        void setView(ProfileMVP.View view);
        void setResources(Resources ressources);
        void getUserData();
        void logoff();
        void deleteAccount();
        void saveUserData();
    }

    interface Model {
        Observable<ReceptUser> getUserData();
        void logoff();
        Observable<ReceptDeleteUser> deleteAccount();
        Observable<ReceptUserUpdate> saveUserData(User user);
    }
}
