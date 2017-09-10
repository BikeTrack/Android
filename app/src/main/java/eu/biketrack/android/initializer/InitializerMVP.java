package eu.biketrack.android.initializer;

import android.content.Context;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

public interface InitializerMVP {

    interface View {
        void openAutoLoginFragment();
    }

    interface Presenter {

        void initApplication(Context context);

        void setView(InitializerMVP.View view);
    }

    interface Model {
        void language(Context context);

        void initLoginManager(Context context);
    }
}
