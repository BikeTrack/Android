package eu.biketrack.android.autologin.AutoLogin;

import eu.biketrack.android.models.User;
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
    }

    interface Model {
        void fillSession(User user);
        void getUserFromNetwork(String userid, String token);

    }
}
