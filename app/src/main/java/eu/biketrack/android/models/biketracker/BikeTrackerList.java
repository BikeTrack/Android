package eu.biketrack.android.models.biketracker;

import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;

import javax.inject.Inject;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;
import eu.biketrack.android.session.LoginManagerModule;


/**
 * Created by 42900 on 14/10/2017 for BikeTrack_Android.
 */
/*

    Ajouter interface + classe pour update les données.

 */
public class BikeTrackerList {

    public interface BikeTrackerListListener {
        void listUpdated();
        void bikeCreated();
    }

    private static final String TAG = "BikeTrackerList";
    private static BikeTrackerList INSTANCE;

    private ArrayList<Pair<Bike, Tracker>> bikeArrayList;
    protected BikeTrackerListListener listener;

    public BikeTrackerList(){
        bikeArrayList = new ArrayList<>();
    }

    public static BikeTrackerList getInstance(){
        if (INSTANCE == null)
            INSTANCE = new BikeTrackerList();
        return INSTANCE;
    }

    public void setBikeTrackerListListener(BikeTrackerListListener bikeTrackerListListener){
        this.listener = bikeTrackerListListener;
    }

    public ArrayList<Pair<Bike, Tracker>> getBikeArrayList() {
        return bikeArrayList;
    }

    public void clear(){
        bikeArrayList.clear();
    }

    public void addPair(Bike b, Tracker t){
        bikeArrayList.add(new Pair<>(b, t));
    }

    public int size(){
        return bikeArrayList.size();
    }

    public Pair<Bike, Tracker> getPair(int position){
        return bikeArrayList.get(position);
    }

    public Bike getBike(int position){
        return bikeArrayList.get(position).first;
    }

    public Tracker getTracker(int position){
        return bikeArrayList.get(position).second;
    }

    public Bike getBikeByBikeId(String bikeId){
        for (int i = 0; i < size(); ++i){
            if (bikeArrayList.get(i).first.getId().equals(bikeId))
                return bikeArrayList.get(i).first;
        }
        return null;
    }

    public Tracker getTrackerByBikeId(String bikeId){
        for (int i = 0; i < size(); ++i){
            if (bikeArrayList.get(i).first.getId().equals(bikeId))
                return bikeArrayList.get(i).second;
        }
        return null;
    }
}
