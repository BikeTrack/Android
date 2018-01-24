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
        String get_email();

        void set_email(String _email);

        String get_lastname();

        void set_lastname(String _lastname);

        String get_firstname();

        void set_firstname(String _firstname);

        String get_dob();

        void set_dob(String _dob);

        String getAlertMail();

        void setAlertMail(String alertMail);

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
