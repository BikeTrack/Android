package eu.biketrack.android.bike;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import eu.biketrack.android.R;

/**
 * Created by 42900 on 13/10/2017 for BikeTrack_Android.
 */

public class BikeCollectionActivity extends FragmentActivity {

    BikeCollectionPageAdapter mBikeCollectionPagerAdapter;
    ViewPager mViewPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_bike);

        // ViewPager and its adapters use support library
        // fragments, so use getSupportFragmentManager.
        mBikeCollectionPagerAdapter = new BikeCollectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mBikeCollectionPagerAdapter);
    }
}
