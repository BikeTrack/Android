package eu.biketrack.android.settings.profile_tab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.initializer.Initializer;
import eu.biketrack.android.root.App;

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
        return layout;
    }
//

    @Override
    public void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.setResources(getResources());
        presenter.getUserData();
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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.log_off_button)
    public void logoff(){
        presenter.logoff();
    }

    @Override
    public void close(){
        Intent autologin_intent = new Intent(getActivity(), Initializer.class);
        autologin_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(autologin_intent);
    }

    @OnClick(R.id.delete_account_button)
    public void confirmDeleteAccount(){
        new AlertDialog.Builder(this.getContext())
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.alert_confirmation_delete_account_title)
                .setMessage(R.string.alert_confirmation_delete_account_message)
                .setPositiveButton(R.string.alert_confirmation_delete_account_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        presenter.deleteAccount();
                    }
                })
                .setNegativeButton(R.string.alert_confirmation_delete_account_no, null)
                .show();
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
        Snackbar.make(this.getView(), message, Snackbar.LENGTH_LONG).show();
    }
}
