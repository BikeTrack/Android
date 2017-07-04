package eu.biketrack.android.activities;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import eu.biketrack.android.R;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;

import static eu.biketrack.android.api_connection.Statics.BATTERY_CRITICAL;
import static eu.biketrack.android.api_connection.Statics.BATTERY_LOW;

/**
 * Created by 42900 on 04/12/2016 for BikeTrack_Android.
 */

public class CustomListAdapter extends ArrayAdapter<Pair<Bike, Tracker>> {

    private final Activity context;
    private final ArrayList<Pair<Bike, Tracker>> bikes;

    public CustomListAdapter(Activity context, ArrayList<Pair<Bike, Tracker>> bikes) {
        super(context, R.layout.listbike, bikes);
        this.context = context;
        this.bikes = bikes;
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listbike, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        ImageView battery = (ImageView) rowView.findViewById(R.id.battery);
//        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(bikes.get(position).first.getBrand() + " " + bikes.get(position).first.getName());


        imageView.setImageResource(R.drawable.ic_logo_black);
        if (battery == null){
            return rowView;
        } else if (bikes.get(position).second != null){
            battery.setImageResource(R.drawable.ic_broken_link);
            return rowView;
        } else if (bikes.get(position).second.getBattery().get(bikes.get(position).second.getBattery().size() - 1) != null){
            battery.setImageResource(R.drawable.ic_broken_link);
            return rowView;
        }


        if (bikes.get(position).second.getBattery().get(bikes.get(position).second.getBattery().size() - 1).getPourcentage() < BATTERY_CRITICAL){
            battery.setImageResource(R.drawable.ic_battery_critical);
        } else if (bikes.get(position).second.getBattery().get(bikes.get(position).second.getBattery().size() - 1).getPourcentage() < BATTERY_LOW){
            battery.setImageResource(R.drawable.ic_battery_low);
        } else {
            battery.setImageResource(R.drawable.ic_battery_full);
        }
        //extratxt.setText("Latitude : " + bikes.get(position).getLat() + " Longitude : " + bikes.get(position).getLong());
        return rowView;
    }
}
