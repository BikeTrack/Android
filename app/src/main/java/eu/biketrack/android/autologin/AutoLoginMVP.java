package eu.biketrack.android.autologin;

import eu.biketrack.android.session.LoginManagerModule;
import eu.biketrack.android.session.Session;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public interface AutoLoginMVP {

    interface View{
        void displayProgressBar(boolean display);
        void openLogin();
        void openBikes();
    }

    interface Presenter{
        void setView(AutoLoginMVP.View view);
        void setSession(Session session);
        void setLoginManager(LoginManagerModule loginManagerModule);
        void tryConnection();
        void viewAfterGettingUser();
    }

    interface Model {
        void setPresenter(AutoLoginMVP.Presenter presenter);
        void fillSession(String userId, String token);
        void getUserFromNetwork(String userId, String token);
        Throwable getError();

    }
}
