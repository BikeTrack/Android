package eu.biketrack.android.settings.profile_tab;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.initializer.Initializer;
import eu.biketrack.android.root.App;

public class ProfileFragment extends Fragment implements ProfileMVP.View {
    private static String TAG = "BIKETRACK - Profile";
    @BindView(R.id.profile_email_tv)
    TextView _email;
    @BindView(R.id.profile_lastname_tv)
    TextView _lastname;
    @BindView(R.id.profile_firstname_tv)
    TextView _firstname;
    @BindView(R.id.profile_dob_tv)
    TextView _dob;
    @BindView(R.id.profile_alertMail_tv)
    TextView alertMail;
    @Inject
    ProfileMVP.Presenter presenter;
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.log_off_button)
    public void logoff() {
        presenter.logoff();
    }

    @Override
    public void close() {
        Intent autologin_intent = new Intent(getActivity(), Initializer.class);
        autologin_intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(autologin_intent);
    }

    @OnClick(R.id.delete_account_button)
    public void confirmDeleteAccount() {
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
    public void set_email(String _email) {
        this._email.setText(_email);
    }

    @Override
    public String get_lastname() {
        return _lastname.getText().toString();
    }

    @Override
    public void set_lastname(String _lastname) {
        this._lastname.setText(_lastname);
    }

    @Override
    public String get_firstname() {
        return _firstname.getText().toString();
    }

    @Override
    public void set_firstname(String _firstname) {
        this._firstname.setText(_firstname);
    }

    @Override
    public String get_dob() {
        return this._dob.getText().toString();
    }

    @Override
    public void set_dob(String _dob) {
        this._dob.setText(_dob);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public void update(){
        presenter.getUserData();
    }

    @Override
    public String getAlertMail() {
        return alertMail.getText().toString();
    }

    @Override
    public void setAlertMail(String alertMail) {
            this.alertMail.setText(alertMail);
    }
}
