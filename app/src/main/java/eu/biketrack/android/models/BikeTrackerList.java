package eu.biketrack.android.models;

import android.support.v4.util.Pair;

import java.util.ArrayList;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;

/**
 * Created by 42900 on 14/10/2017 for BikeTrack_Android.
 */

public class BikeTrackerList {
    private static BikeTrackerList INSTANCE;
    private ArrayList<Pair<Bike, Tracker>> bikeArrayList;

    private BikeTrackerList(){
        bikeArrayList = new ArrayList<>();
    }

    public static BikeTrackerList getInstance(){
        if (INSTANCE == null)
            INSTANCE = new BikeTrackerList();
        return INSTANCE;
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
}
