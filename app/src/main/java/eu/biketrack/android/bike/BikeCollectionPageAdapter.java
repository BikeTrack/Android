package eu.biketrack.android.bike;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import eu.biketrack.android.models.BikeTrackerList;

/**
 * Created by 42900 on 13/10/2017 for BikeTrack_Android.
 */

public class BikeCollectionPageAdapter extends FragmentStatePagerAdapter {
    private BikeTrackerList bikeTrackerList = BikeTrackerList.getInstance();

    public BikeCollectionPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return bikeTrackerList.getPair(position).first.getName();
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new BikeFragment();
        Bundle args = new Bundle();

        args.putInt(BikeFragment.ARG_BIKE, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
