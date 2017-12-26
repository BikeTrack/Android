package eu.biketrack.android.settings.profile_tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.models.User;
import eu.biketrack.android.root.App;


public class EditProfileFragment extends Fragment implements ProfileMVP.View{
    private static String TAG = "BIKETRACK - EditProfile";
    private Unbinder unbinder;

    @BindView(R.id.profile_email_edit)
    EditText _email;
    @BindView(R.id.profile_lastname_edit)
    EditText _lastname;
    @BindView(R.id.profile_firstname_edit)
    EditText _firstname;

    @Inject
    ProfileMVP.Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        unbinder = ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.setResources(getResources());
        presenter.getUserData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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
    public void set_dob(String _dob) {

    }

    @Override
    public void close() {

    }

    @OnClick(R.id.save_account_button)
    public void saveAccount(){
        presenter.saveUserData();
    }

    @Override
    public String get_email() {
        return _email.getText().toString();
    }

    @Override
    public String get_lastname() {
        return _lastname.getText().toString();
    }

    @Override
    public String get_firstname() {
        return _firstname.getText().toString();
    }

    @Override
    public String get_dob() {
        return null;
    }

    @Override
    public void displayMessage(String message){
        if (this.getView() != null)
            Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
        else
            Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }
}
