package eu.biketrack.android.login;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public interface LoginMVP {

    interface View{
        String getUserEmail();
        String getUserPassword();
        void close();
    }

    interface Presenter{
        void setView(LoginMVP.View view);
        void connexionButtonClicked();

    }

    interface Model {
        void connection(String email, String password);
    }
}
