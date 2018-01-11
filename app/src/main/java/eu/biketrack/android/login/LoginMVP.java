package eu.biketrack.android.login;

/**
 * Created by 42900 on 10/09/2017 for BikeTrack_Android.
 */

public interface LoginMVP {

    interface View {
        String getUserEmail();

        String getUserPassword();

        boolean getRememberMe();

        void close();

        void openSubscribe();

        void loading(boolean loading);

        void displayError(Throwable throwable);
    }

    interface Presenter {
        void setView(LoginMVP.View view);

        void connexionButtonClicked();

        void viewAfterConnection();

        void goToSubscribe();

        void facebookClicked(String email, String token, String name, String first_name, String birthday);
    }

    interface Model {
        void setPresenter(LoginMVP.Presenter presenter);

        //        void setBiketrackService(BiketrackService biketrackService);
        void connection(String email, String password, boolean rememberme);

        void subscriptionByFacebook(String fbemail, String fbtoken, String name, String first_name, String birthday);

        Throwable getError();
    }
}
