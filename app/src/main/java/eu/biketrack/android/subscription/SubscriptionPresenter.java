package eu.biketrack.android.subscription;

/**
 * Created by 42900 on 17/09/2017 for BikeTrack_Android.
 */

public class SubscriptionPresenter implements SubscriptionMVP.Presenter {
    private static final String TAG = "SubscriptionPresenter";
    private SubscriptionMVP.View view;
    private SubscriptionMVP.Model model;

    public SubscriptionPresenter(SubscriptionMVP.Model model) {
        this.model = model;
        this.model.setPresenter(this);
    }

    @Override
    public void setView(SubscriptionMVP.View view) {
        this.view = view;
    }

    @Override
    public void subscriptionButtonClicked() {
        if (checkPassword(view.getUserPassword(), view.getUserRepeatPassword()))
            model.subscription(view.getUserEmail(), view.getUserPassword(), view.getRememberMe());
        else {
            view.displayError("!=mdp");
        }
    }

    private boolean checkPassword(String password, String repeat) {
        return password.equals(repeat);
    }

    @Override
    public void loginButtonClicked() {
        goToLogin();
    }

    @Override
    public void viewAfterSubscription(boolean loginDone) {
        if (model.getError() == null) {
            view.close();
        } else {
            view.displayError(model.getError());
        }
    }

    @Override
    public void goToLogin() {
        view.openLogin();
    }
}
