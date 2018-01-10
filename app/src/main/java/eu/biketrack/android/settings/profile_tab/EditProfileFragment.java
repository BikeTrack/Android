package eu.biketrack.android.settings.profile_tab;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.root.App;


public class EditProfileFragment extends Fragment implements ProfileMVP.View, DatePickerDialog.OnDateSetListener {
    private static String TAG = "BIKETRACK - EditProfile";
    @BindView(R.id.profile_email_edit)
    EditText _email;
    @BindView(R.id.profile_lastname_edit)
    EditText _lastname;
    @BindView(R.id.profile_firstname_edit)
    EditText _firstname;
    //    @BindView(R.id.profile_dob_edit)
//    TextView datePicker;
    @BindView(R.id.profile_dobt_edit)
    EditText dob_text;
    @Inject
    ProfileMVP.Presenter presenter;
    private DatePickerDialog datePickerDialog;
    private String dob = "";
    private Unbinder unbinder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getActivity().getApplication()).getComponent().inject(this);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Calendar currentTime = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(getContext(), this, currentTime.get(Calendar.YEAR), currentTime.get(Calendar.MONTH), currentTime.get(Calendar.DAY_OF_MONTH));
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
    public void close() {
        this.getActivity().onContentChanged();
        this.getFragmentManager().popBackStackImmediate();
    }

    @OnClick(R.id.save_account_button)
    public void saveAccount() {
        presenter.saveUserData();
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
        return dob;
    }

    @Override
    public void set_dob(String _dob) {
        dob = _dob;
        try {
            String[] td = dob.split("/|-");
            datePickerDialog.updateDate(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1, Integer.parseInt(td[0]));
        } catch (Exception e) {
            Log.e(TAG, "set_dob: ", e);
        }
        dob_text.setText(dob);
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.profile_dob_edit)
    public void selectDate() {
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String d;
        String m;
        if (dayOfMonth < 10)
            d = "0" + dayOfMonth;
        else
            d = "" + dayOfMonth;
        if (month + 1 < 10)
            m = "0" + (month + 1);
        else
            m = "" + (month + 1);
        dob = d + "/" + m + "/" + year;
        dob_text.setText(dob);
    }
}
