package eu.biketrack.android.bikes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import eu.biketrack.android.R;
import eu.biketrack.android.models.BikeTrackerList;

import static eu.biketrack.android.api_connection.Statics.BATTERY_CRITICAL;
import static eu.biketrack.android.api_connection.Statics.BATTERY_LOW;

/**
 * Created by 42900 on 16/10/2017 for BikeTrack_Android.
 */

public class RecyclerViewBikesAdapter extends RecyclerView.Adapter<RecyclerViewBikesAdapter.MyViewHolder> {

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


        holder.imageView.setImageResource(R.drawable.ic_logo_black);
        if (bikeTrackerList.getBikeArrayList().get(position).second == null){
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
