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
        int position = 0;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                position = 0;
            } else {
                position = extras.getInt("position");
            }
        } else {
            position = (int) savedInstanceState.getSerializable("position");
        }

        mBikeCollectionPagerAdapter = new BikeCollectionPageAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mBikeCollectionPagerAdapter);
        mViewPager.setCurrentItem(position, true);
    }
}
