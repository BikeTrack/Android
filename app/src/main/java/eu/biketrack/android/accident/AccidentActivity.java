package eu.biketrack.android.accident;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnectModule;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_send.CancelEmergency;
import eu.biketrack.android.session.LoginManagerModule;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AccidentActivity extends AppCompatActivity {
    private static final String TAG = "AccidentActivity";
    private String trackerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accident);
        ButterKnife.bind(this);
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                String value = getIntent().getExtras().getString(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
            trackerId = getIntent().getStringExtra("trackerId");
        }
        Log.d(TAG, "onCreate: trackerId = " + trackerId);
    }

    @OnClick(R.id.accident_button_yes)
    public void accidentYes() {
        finish();
    }

    @OnClick(R.id.accident_button_no)
    public void accidentNo() {
        try {
            Log.d(TAG, "accidentNo: trackerId = " + trackerId);
            BiketrackService biketrackService = ApiConnectModule.provideApiService();
            LoginManagerModule loginManagerModule = new LoginManagerModule(getApplication().getApplicationContext());
            CancelEmergency cancelEmergency = new CancelEmergency(trackerId);
            biketrackService.cancelEmergency(cancelEmergency)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(receptUserUpdate -> {
                                Log.d(TAG, "accidentNo: accident canceled");
                                new AlertDialog.Builder(this)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setTitle(getResources().getString(R.string.title_notif_accident_canceled))
                                        .setMessage(getResources().getString(R.string.msg_notif_accident_canceled))
                                        .setOnDismissListener(dialog -> {
                                            finish();
                                        })
                                        .show();
                            },
                            throwable -> {
                                Log.e(TAG, "accidentNo: ", throwable);
                                new AlertDialog.Builder(this)
                                        .setIcon(android.R.drawable.ic_dialog_info)
                                        .setTitle(getResources().getString(R.string.error_title_notif_accident_canceled))
                                        .setMessage(getResources().getString(R.string.error_msg_notif_accident_canceled))
                                        .show();
                            },
                            () -> {

                            });
        } catch (Exception e) {
            Log.e(TAG, "accidentNo: ", e);
        }
    }
}
