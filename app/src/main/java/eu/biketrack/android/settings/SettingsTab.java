package eu.biketrack.android.settings;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.biketrack.android.R;
import eu.biketrack.android.settings.profile_tab.EditProfileFragment;
import eu.biketrack.android.settings.profile_tab.ProfileFragment;
import eu.biketrack.android.settings.settings_tab.SettingsFragment;

public class SettingsTab extends FragmentActivity {
    private static final String TAG = "SettingsTab";
    @BindView(R.id.settings_floating_button)
    public FloatingActionButton settingsFloatingButton;
    @BindView(R.id.container)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onResume() {
        try {
            super.onResume();
            Fragment f = ((SectionsPagerAdapter) mViewPager.getAdapter()).getCurrentFragment();
            f.onResume();
        }catch (Exception e){
            Log.e(TAG, "onResume: ", e);
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_tab);
        ButterKnife.bind(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSectionsPagerAdapter);
        settingsFloatingButton.setVisibility(View.VISIBLE);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                settingsFloatingButtonVisibility(position);
                try {
                    Fragment f = ((SectionsPagerAdapter) mViewPager.getAdapter()).getCurrentFragment();
                    f.onResume();
                }catch (Exception e){
                    Log.e(TAG, "onPageSelected: ", e);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        settingsFloatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + mViewPager.getCurrentItem());
                switch (mViewPager.getCurrentItem()) {
                    case 0:
                        openEditProfileFragment();
                        break;
                    case 1:
                        break;
//                    case 2:
//                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        try {
            mViewPager.setCurrentItem(0);
            Fragment f = ((SectionsPagerAdapter) mViewPager.getAdapter()).getProfileFragment();
            Log.d(TAG, "onContentChanged: " + f);
            f.onResume();
        }catch (Exception e){
            Log.e(TAG, "onContentChanged: ", e);
        }

    }

    private void openEditProfileFragment() {
        Fragment fragment = new EditProfileFragment();
        final String tag = fragment.getClass().toString();
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }



    protected void settingsFloatingButtonVisibility(int position) {
        Log.d(TAG, "settingsFloatingButtonVisibility: page =" + position);
        switch (position) {
            case 0:
                settingsFloatingButton.setVisibility(View.VISIBLE);
                break;
//            case 1:
//                settingsFloatingButton.setVisibility(View.GONE);
//                break;
            case 1:
                settingsFloatingButton.setVisibility(View.GONE);
                break;
            default:
                settingsFloatingButton.setVisibility(View.GONE);
                break;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private int currentPage = 0;
        private ProfileFragment profileFragment = null;
//        private EmergencyFragment emergencyFragment = null;
        private SettingsFragment settingsFragment = null;

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            currentPage = position;
            switch (position) {
                case 0:
                    this.profileFragment = new ProfileFragment();
                    return this.profileFragment;
//                case 1:
//                    this.emergencyFragment = new EmergencyFragment();
//                    return this.emergencyFragment;
                case 1:
                    this.settingsFragment = new SettingsFragment();
                    return this.settingsFragment;
                default:
                    currentPage = 0;
                    this.profileFragment = new ProfileFragment();
                    return this.profileFragment;
            }
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public Fragment getCurrentFragment(){
            if( getCurrentPage() == 0)
                return this.profileFragment;
//            else if (getCurrentPage() == 1)
//                return this.emergencyFragment;
            else if (getCurrentPage() == 1)
                return this.settingsFragment;
            else
                return null;
        }

        public ProfileFragment getProfileFragment() {
            return profileFragment;
        }

        @Override
        public int getCount() {
            return 2;
        }
    }
}
