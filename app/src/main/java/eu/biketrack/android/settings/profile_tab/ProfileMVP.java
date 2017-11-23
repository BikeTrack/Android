package eu.biketrack.android.settings.profile_tab;

/**
 * Created by 42900 on 17/11/2017 for BikeTrack_Android.
 */

public interface ProfileMVP {
    interface View {
        void set_email(String _email);
        void set_lastname(String _lastname);
        void set_firstname(String _firstname);
    }

    interface Presenter {
        void setView(ProfileMVP.View view);
        void getUserData();
        void logoff();
    }

    interface Model {
        void getUserData();
        void logoff();
    }
}
