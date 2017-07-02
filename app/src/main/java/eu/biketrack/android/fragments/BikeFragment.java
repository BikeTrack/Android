package eu.biketrack.android.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.LobwickService;
import eu.biketrack.android.models.SigfoxData;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.session.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;


public class BikeFragment extends Fragment implements OnMapReadyCallback {
    private static String TAG = "BIKETRACK - Bike";
    private Unbinder unbinder;
    private Session session;
    private Bike bike;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private LobwickService lobwickService;
    private List<SigfoxData> sigfoxDataList;

    private static final int REQUEST_LOCATION = 1;

    @BindView(R.id.bike_name_tv) TextView _name;
//    @BindView(R.id.bike_colour_tv) TextView _colour;
//    @BindView(R.id.bike_brand_tv) TextView _brand;
//    @BindView(R.id.bike_trackerid_tv) TextView _trackerid;
    @BindView(R.id.map) MapView mapView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biketrackService = ApiConnect.createService();
        lobwickService = ApiConnect.createServiceLobwick();
        _disposables = new CompositeDisposable();

        Bundle bundle = getArguments();
        session = Session.getInstance();
        bike = bundle.getParcelable("BIKE");
        getCoordinates();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bike, container, false);
        unbinder = ButterKnife.bind(this, layout);

        _name.setText(bike.getBrand() + " " + bike.getName());
//        _colour.setText(bike.getColor());
//        _brand.setText(bike.getBrand());
//        _trackerid.setText(bike.getTracker());
        mapView.onCreate(savedInstanceState);

        return layout;
    }

    @Override
    public void onDestroyView() {
        mapView.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        int i = 0;
        LatLng target = null;
        for (SigfoxData sigfoxData : sigfoxDataList) {
                try {
                    if (!sigfoxData.getLatitude().equals("80.0") && !sigfoxData.getLongitude().equals("-150.0") || !sigfoxData.getLatitude().equals("-62.0") && !sigfoxData.getLongitude().equals("-150.0") ) {
                        Log.d(TAG, sigfoxData.getLatitude() + sigfoxData.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(sigfoxData.getLatitude()), Double.parseDouble(sigfoxData.getLongitude()))));
                        if (i == 0)
                            target = new LatLng(Double.parseDouble(sigfoxData.getLatitude()), Double.parseDouble(sigfoxData.getLongitude()));
                        i++;
                    }
                } catch (java.lang.NumberFormatException nfe){
                    Log.i(TAG, "Error ", nfe);
                }
            if (i > 10)
                break;
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(
                getActivity(),
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getActivity(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
            return ;
        }
        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        MapsInitializer.initialize(this.getActivity());
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        if (target == null)
            target = new LatLng(0.0,0.0);
        builder.include(target);
        LatLngBounds bounds = builder.build();
        int padding = 0;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        googleMap.moveCamera(cameraUpdate);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // TODO: 20/06/2017 find a way to redraw the map or reload the fragment
                }
            }
        }
    }

    public void getCoordinates(){
        _disposables.add(
                lobwickService.getSigfoxDatas()
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<List<SigfoxData>>() {

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Login completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while getting coordinates ", e);
                            }

                            @Override
                            public void onNext(List<SigfoxData> sigfoxDatas) {
//                                Log.d(TAG, sigfoxDatas.toString());
//                                for(SigfoxData sigfoxData : sigfoxDatas){
//                                    Log.d(TAG, sigfoxData.toString());
//                                }
                                Collections.reverse(sigfoxDatas);
                                sigfoxDataList = sigfoxDatas;
                                mapView.getMapAsync(BikeFragment.this);
                            }
                        })
        );
    }


    @OnClick(R.id.back_view_pager)
    public void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
