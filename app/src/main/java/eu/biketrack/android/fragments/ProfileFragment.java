package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.activities.AutoLogin;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.session.LoginManager;
import eu.biketrack.android.session.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class ProfileFragment extends Fragment {
    private static String TAG = "BIKETRACK - Profile";
    private CompositeDisposable _disposables;
    private Unbinder unbinder;
    private Session session;
    private BiketrackService biketrackService;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.profile_email_tv)
    TextView _email;

    @BindView(R.id.profile_lastname_tv)
    TextView _lastname;

    @BindView(R.id.profile_firstname_tv)
    TextView _firstname;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        session = Session.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, layout);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();

        toolbar.inflateMenu(R.menu.profile_menu);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, item.toString());
                int selected_option = item.getItemId();

                if (selected_option == R.id.action_edit_profile){
                    Fragment fragment = new EditProfileFragment();
                    final String tag = fragment.getClass().toString();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(tag)
                            .replace(android.R.id.content, fragment, tag)
                            .commit();
                }
                return true;
            }
        });
        return layout;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bottomNavigationView.getMenu().getItem(0).setEnabled(false);
        bottomNavigationView.getMenu().getItem(0).setChecked(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(false);
        bottomNavigationView.getMenu().getItem(2).setChecked(true);
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
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(android.R.id.content, new BikesFragment(), this.toString())
                                        .commit();
                                break;
                            case R.id.action_profile:
                                break;
                        }
                        return true;
                    }
                });
        getUser();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void getUser() {
        _disposables.add(
                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ReceptUser>() {

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Login completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while getting user info", e);
                                //check error type and raise toast
                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
                                    Toast.makeText(getActivity(), "Wrong user ?", Toast.LENGTH_SHORT).show();
                                else if (e.getMessage().equals("HTTP 404 Not Found"))
                                    Toast.makeText(getActivity(), "You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(ReceptUser receptUser) {
                                if (_email != null)
                                    _email.setText(receptUser.getUser().getMail());
                                if (_lastname != null)
                                    _lastname.setText(receptUser.getUser().getLastname());
                                if (_firstname != null)
                                    _firstname.setText(receptUser.getUser().getName());
                            }
                        })
        );
    }

    @OnClick(R.id.log_off_button)
    public void logoff(){
        session.clear();
        LoginManager loginManager = LoginManager.getInstance();
        loginManager.clear();
        Intent autologin_intent = new Intent(getActivity(), AutoLogin.class);
        startActivity(autologin_intent);
        getActivity().finish();
    }
}
