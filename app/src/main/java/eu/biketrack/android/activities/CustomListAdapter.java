package eu.biketrack.android.activities;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import eu.biketrack.android.R;
import eu.biketrack.android.models.data_reception.Bike;

/**
 * Created by 42900 on 04/12/2016 for BikeTrack_Android.
 */

public class CustomListAdapter extends ArrayAdapter<Bike> {

    private final Activity context;
    private final ArrayList<Bike> bikes;

    public CustomListAdapter(Activity context, ArrayList<Bike> bikes) {
        super(context, R.layout.listbike, bikes);
        // TODO Auto-generated constructor stub

        this.context = context;
        this.bikes = bikes;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listbike, null, true);

        Log.d("YOLO", "position = " + position);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(bikes.get(position).getName());
        //imageView.setImageResource(imgid[position]);
        extratxt.setText("Latitude : " + bikes.get(position).getLat() + " Longitude : " + bikes.get(position).getLong());
        return rowView;
    };
}
