package eu.biketrack.android.fragments;

import android.support.v4.app.Fragment;

import com.squareup.leakcanary.RefWatcher;

import eu.biketrack.android.activities.BikeTrack;

/**
 * Created by 42900 on 07/02/2017 for BikeTrack_Android.
 */

public class BaseFragment extends Fragment
    {

        @Override
        public void onDestroy() {
        super.onDestroy();
        RefWatcher refWatcher = BikeTrack.getRefWatcher();
        refWatcher.watch(this);
    }
}
