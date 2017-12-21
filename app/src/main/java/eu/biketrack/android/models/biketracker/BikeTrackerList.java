package eu.biketrack.android.models.biketracker;

import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Tracker;


/**
 * Created by 42900 on 14/10/2017 for BikeTrack_Android.
 */
/*

    Ajouter interface + classe pour update les données.

 */
public class BikeTrackerList {
    private BikeTrackerNetworkInterface bikeTrackerNetworkInterface;
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
        Log.d(TAG, bikeArrayList.toString());
    }

    public void addPair(Bike b, Tracker t){
        bikeArrayList.add(new Pair<>(b, t));
        sortList();
    }

    public void updateBike(Bike bike){
        boolean found = false;
        for (int i = 0; i < bikeArrayList.size(); i++){
            if (bikeArrayList.get(i).first.getId().equals(bike.getId())){
                bikeArrayList.get(i).first.copy(bike);
                found = true;
            }
        }
        if (!found){
            addPair(bike, new Tracker());
        }
    }

    public void updateTracker(Tracker tracker){
        boolean found = false;
        for (int i = 0; i < bikeArrayList.size(); i++){
            if (bikeArrayList.get(i).first.getTracker().equals(tracker.getId())){
                bikeArrayList.get(i).second.copy(tracker);
                found = true;
            }
        }
        if (!found){
            Log.e(TAG, "updateTracker: Tracker not found");
        }
    }

    public void updateBikePicture(String bikeId, String picture){
        boolean found = false;
        for (Pair<Bike, Tracker> p : bikeArrayList){
            if (p.first.getId().equals(bikeId)){
                p.first.setPicture(picture);
                found = true;
            }
        }
        if (!found){
            Log.e(TAG, "updateBikePicture: Bike not found");
        }
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

    private void sortList(){
        Collections.sort(bikeArrayList, new Comparator<Pair<Bike, Tracker>>() {
            @Override
            public int compare(final Pair<Bike, Tracker> o1, final Pair<Bike, Tracker> o2) {
                return o1.first.getId().compareTo(o2.first.getId());
            }
        });

    }

    public BikeTrackerNetworkInterface getBikeTrackerNetworkInterface() {
        return bikeTrackerNetworkInterface;
    }

    public void setBikeTrackerNetworkInterface(BikeTrackerNetworkInterface bikeTrackerNetworkInterface) {
        this.bikeTrackerNetworkInterface = bikeTrackerNetworkInterface;
    }
}
