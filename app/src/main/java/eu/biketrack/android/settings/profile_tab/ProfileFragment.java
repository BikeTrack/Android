package eu.biketrack.android.settings.profile_tab;

import android.os.Bundle;
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

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.bikes.BikesMVP;
import eu.biketrack.android.root.App;
import eu.biketrack.android.session.Session;

public class ProfileFragment extends Fragment implements ProfileMVP.View{
    private static String TAG = "BIKETRACK - Profile";
    private Unbinder unbinder;

    @BindView(R.id.profile_email_tv)
    TextView _email;

    @BindView(R.id.profile_lastname_tv)
    TextView _lastname;

    @BindView(R.id.profile_firstname_tv)
    TextView _firstname;

    @Inject
    ProfileMVP.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
//        session = Session.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, layout);
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();

//        toolbar.inflateMenu(R.menu.profile_menu);
//
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                Log.d(TAG, item.toString());
//                int selected_option = item.getItemId();
//
//                if (selected_option == R.id.action_edit_profile){
//                    Fragment fragment = new EditProfileFragment();
//                    final String tag = fragment.getClass().toString();
//                    getActivity().getFragmentManager()
//                            .beginTransaction()
//                            .addToBackStack(tag)
//                            .replace(android.R.id.content, fragment, tag)
//                            .commit();
//                }
//                return true;
//            }
//        });
        return layout;
    }
//

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @Override
    public void set_email(String _email) {
        this._email.setText(_email);
    }

    @Override
    public void set_lastname(String _lastname) {
        this._lastname.setText(_lastname);
    }

    @Override
    public void set_firstname(String _firstname) {
        this._firstname.setText(_firstname);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
//
//    private void getUser() {
//        _disposables.add(
//                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceptUser>() {
//
//                            @Override
//                            public void onComplete(){
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(ReceptUser receptUser) {
//                                if (_email != null)
//                                    _email.setText(receptUser.getUser().getMail());
//                                if (_lastname != null)
//                                    _lastname.setText(receptUser.getUser().getLastname());
//                                if (_firstname != null)
//                                    _firstname.setText(receptUser.getUser().getName());
//                            }
//                        })
//        );
//    }
//
//    @OnClick(R.id.log_off_button)
//    public void logoff(){
//        session.clear();
//        LoginManagerModule loginManager = LoginManagerModule.getInstance();
//        loginManager.clear();
//        Intent autologin_intent = new Intent(getActivity(), Initializer.class);
//        startActivity(autologin_intent);
//        getActivity().finish();
//    }
}
