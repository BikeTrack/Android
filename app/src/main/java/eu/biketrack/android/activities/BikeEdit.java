package eu.biketrack.android.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.models.data_reception.Bike;

public class BikeEdit extends AppCompatActivity {

    private EditText _name_bike;
    private Button _validate_button;
    private AsyncAddBikeTask asyncAddBikeTask;
    private ApiConnect api;
    private eu.biketrack.android.models.data_send.Bike actual_bike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike_edit);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        api = new ApiConnect();

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String auth_token_string = settings.getString("API_TOKEN", null);
        api.setToken(auth_token_string);

        actual_bike = new eu.biketrack.android.models.data_send.Bike();

        _name_bike = (EditText) findViewById(R.id.bike_name_edit);
        _validate_button = (Button) findViewById(R.id.button_valid_change_bike);
        _validate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (_name_bike.getText().length() > 0) {
                    System.out.println("VALIDER = " + _name_bike.getText());
                    actual_bike.setName(_name_bike.getText().toString());
                    actual_bike.setLatitude(0);
                    actual_bike.setLongitude(0);
                    addNewBike();
                }
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void addNewBike(){
        asyncAddBikeTask = new AsyncAddBikeTask(this, api, actual_bike);
        asyncAddBikeTask.execute((Void) null);
    }


    public class AsyncAddBikeTask extends AsyncTask<Void, Void, Boolean> {
        private ApiConnect api;
        private Activity activity;
        private eu.biketrack.android.models.data_send.Bike actual_bike;

        AsyncAddBikeTask(Activity activity, ApiConnect apiConnect, eu.biketrack.android.models.data_send.Bike bike) {
            this.activity = activity;
            api = apiConnect;
            actual_bike = bike;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                api.addBike(actual_bike);
            } catch (Exception e) {
//                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG);
                Log.e("BIKETRACK NETWORK", "Get Bike error", e);
                return false;
            }


            // TODO: register the new account here.
            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            finish();
        }

        @Override
        protected void onCancelled() {

        }
    }
}
