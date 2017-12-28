package eu.biketrack.android.subscription;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

public interface SubscriptionMVP {
    interface View{
        String getUserEmail();
        String getUserPassword();
        String getUserRepeatPassword();
        boolean getRememberMe();
        void close();
        void openLogin();
        void displayError(String message);
    }

    interface Presenter{
        void setView(SubscriptionMVP.View view);
        void subscriptionButtonClicked();
        void loginButtonClicked();
        void viewAfterSubscription(boolean loginDone);
        void goToLogin();
    }

    interface Model {
        void setPresenter(SubscriptionMVP.Presenter presenter);
        void subscription(String email, String password, boolean rememberme);
        Throwable getError();
    }
}
