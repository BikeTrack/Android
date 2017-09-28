package eu.biketrack.android.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.bikes.Bikes;


public class SettingsFragment extends Fragment {

    private static String TAG = "BIKETRACK - Settings";
    private Unbinder unbinder;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_settings, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(false);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
                        bottomNavigationView.getMenu().getItem(1).setChecked(false);
                        bottomNavigationView.getMenu().getItem(2).setChecked(false);
                        switch (item.getItemId()) {
                            case R.id.action_settings:

                                break;
                            case R.id.action_bikes:
//                                getActivity().getSupportFragmentManager().beginTransaction()
//                                        .replace(android.R.id.content, new Bikes(), this.toString())
//                                        .commit();
                                break;
                            case R.id.action_profile:
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(android.R.id.content, new ProfileFragment(), this.toString())
                                        .commit();
                                break;
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.button_open_language)
    public void openLanguage(){
        Fragment fragment = new LanguageFragment();
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }
}
