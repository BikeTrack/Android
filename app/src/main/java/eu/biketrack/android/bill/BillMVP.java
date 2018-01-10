package eu.biketrack.android.bill;

import android.graphics.Bitmap;

/**
 * Created by 42900 on 29/12/2017 for BikeTrack_Android.
 */

public class BillMVP {
    interface View {
        void close();

        void progressBarVisibility(boolean visible);

        void setBitmap(Bitmap bitmap);

        void displayBitmapNull();
    }

    interface Presenter {
        void setView(BillMVP.View view);

        void loadBill(String bikeId);
    }

    interface Model {
        void loadBill(String bikeId);
    }
}
