package eu.biketrack.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.activities.CustomListAdapter;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.Bike;
import io.reactivex.disposables.CompositeDisposable;

public class BikesFragment extends Fragment {

    private static String TAG = "BIKETRACK - Bikes";

//    private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
    private Unbinder unbinder;
    private ArrayList<Bike> arrayList = new ArrayList<>();
    CallbackManager callbackManager;
    @BindView(R.id.listView_bikes) ListView list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bikes, container, false);
        unbinder = ButterKnife.bind(this, layout);


        Bike b1 = new Bike();
        b1.setName("Bike 1");
        Bike b2 = new Bike();
        b2.setName("Bike 2");
        arrayList.add(b1);
        arrayList.add(b2);

        CustomListAdapter adapter = new CustomListAdapter(this.getActivity(), arrayList);
        list.setAdapter(adapter);

        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnItemClick(R.id.listView_bikes)
    public void selectBike(int position){
        Log.d(TAG, "select bike position = " + position);
    }

}
