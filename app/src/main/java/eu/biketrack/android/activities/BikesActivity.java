package eu.biketrack.android.activities;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.models.data_reception.Bike;

public class BikesActivity extends AppCompatActivity {
/*
    ListView list;

    private AsyncBikesTask abt;
    private ApiConnect api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //api = (ApiConnect) getIntent().getSerializableExtra("API");
        api = new ApiConnect();

        SharedPreferences settings = PreferenceManager
                .getDefaultSharedPreferences(getApplicationContext());
        String auth_token_string = settings.getString("API_TOKEN", null);
        api.setToken(auth_token_string);

        abt = new AsyncBikesTask(this, api);
        abt.execute((Void) null);


//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                // TODO Auto-generated method stub
//                String Slecteditem= itemname[+position];
//                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();
//
//            }
//        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                openBikeEdit();
                //list.setAdapter(adapterb);
            }
        });
    }

    private void openBikeEdit(){
        Intent intent = new Intent(this , BikeEdit.class);
//        intent.putExtra("API", api);
        startActivity(intent);
    }

    public class AsyncBikesTask extends AsyncTask<Void, Void, Boolean> {
        private ApiConnect api;
        private Activity activity;

        AsyncBikesTask(Activity activity, ApiConnect apiConnect) {
            this.activity = activity;
            api = apiConnect;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                api.getBikes();
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
            CustomListAdapter adapter = new CustomListAdapter(activity, api.getBike().toArray(new Bike[api.getBike().size()]));
            list=(ListView)findViewById(R.id.listView_bikes);
            list.setAdapter(adapter);
        }

        @Override
        protected void onCancelled() {

        }
    }
*/
}

