package eu.biketrack.android.bikes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.biketracker.BikeTrackerNetwork;
import eu.biketrack.android.session.LoginManagerModule;

import static eu.biketrack.android.api_connection.Statics.BATTERY_CRITICAL;
import static eu.biketrack.android.api_connection.Statics.BATTERY_LOW;

/**
 * Created by 42900 on 16/10/2017 for BikeTrack_Android.
 */

public class RecyclerViewBikesAdapter extends RecyclerView.Adapter<RecyclerViewBikesAdapter.MyViewHolder> {
    private static final String TAG = "RecyclerViewBikesAdapte";
    private BikeTrackerList bikeTrackerList;
//    private ArrayList<Pair<Bike, Tracker>> bikes;
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         @BindView(R.id.item)
        TextView txtTitle;

        @BindView(R.id.icon)
        ImageView imageView;

        @BindView(R.id.battery)
        ImageView battery;

        public MyViewHolder(View view) {
            super(view);

            // binding view
            ButterKnife.bind(this, view);
        }

        @Override
        public void onClick(View v) {
        int position = getAdapterPosition();

        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
//    public RecyclerViewBikesAdapter(ArrayList<Pair<Bike, Tracker>> bikes) {
//        this.bikes = bikes;
//    }


    public RecyclerViewBikesAdapter() {
        bikeTrackerList = BikeTrackerList.getInstance();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecyclerViewBikesAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listbike, parent, false);
        return new MyViewHolder(rowView);
    }


    @Override
    public void onBindViewHolder(RecyclerViewBikesAdapter.MyViewHolder holder, int position) {

        holder.txtTitle.setText(bikeTrackerList.getBikeArrayList().get(position).first.getName());

//        bikeTrackerList.getBikeTrackerNetworkInterface().displayImage("/testPicturePhoto", holder.imageView);
//        holder.imageView.setImageResource(R.drawable.ic_logo_black);
//        holder.imageView.setImageBitmap(bikeTrackerList.getBikeArrayList().get(position).first.getPicture());
//        Log.d("sqd", "onBindViewHolder: " + bikeTrackerList.getBikeArrayList().get(position).first.getPicture());
        try {
            Log.d(TAG, "onBindViewHolder: " + bikeTrackerList.getBikeArrayList().get(position).first.getPicture());
            byte[] decodedString = Base64.decode(bikeTrackerList.getBikeArrayList().get(position).first.getPicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.imageView.setImageBitmap(decodedByte);
        } catch (Exception e){
            Log.e(TAG, "onBindViewHolder: Problem with bitmap");
//            Log.e(TAG, "onBindViewHolder: ",e );
        }

        if (bikeTrackerList.getBikeArrayList().get(position).second == null){
            holder.battery.setImageResource(R.drawable.ic_broken_link);
            return;
        }
        else if (bikeTrackerList.getBikeArrayList().get(position).second.getId() == null){
            holder.battery.setImageResource(R.drawable.ic_broken_link);
            return;
        }
        else if (bikeTrackerList.getBikeArrayList().get(position).second.getBattery().get(bikeTrackerList.getBikeArrayList().get(position).second.getBattery().size() - 1) == null){
            holder.battery.setImageResource(R.drawable.ic_broken_link);
            return;
        }


        if (bikeTrackerList.getBikeArrayList().get(position).second.getBattery().get(bikeTrackerList.getBikeArrayList().get(position).second.getBattery().size() - 1).getPourcentage() < BATTERY_CRITICAL){
            holder.battery.setImageResource(R.drawable.ic_battery_critical);
        } else if (bikeTrackerList.getBikeArrayList().get(position).second.getBattery().get(bikeTrackerList.getBikeArrayList().get(position).second.getBattery().size() - 1).getPourcentage() < BATTERY_LOW){
            holder.battery.setImageResource(R.drawable.ic_battery_low);
        } else {
            holder.battery.setImageResource(R.drawable.ic_battery_full);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return bikeTrackerList.getBikeArrayList().size();
    }


}
