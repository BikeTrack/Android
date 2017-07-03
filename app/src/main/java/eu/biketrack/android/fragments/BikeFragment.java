package eu.biketrack.android.fragments;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
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
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.SigfoxData;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.session.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


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

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bike_picture)
    ImageView _bike_picture;
    @BindView(R.id.bike_name_tv) TextView _name;
    @BindView(R.id.map) MapView mapView;
    @BindView(R.id.date_last_point) TextView _date_last_point;

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
        toolbar.inflateMenu(R.menu.bike_menu);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG, item.toString());
                int selected_option = item.getItemId();

                if (selected_option == R.id.action_edit_bike){
                    Bundle bundle = new Bundle();
                    bundle.putParcelable("BIKE", bike);
                    Fragment fragment = new EditBikeFragment();
                    fragment.setArguments(bundle);
                    final String tag = fragment.getClass().toString();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(tag)
                            .replace(android.R.id.content, fragment, tag)
                            .commit();
                } else if (selected_option == R.id.action_delete_bike){
                    new AlertDialog.Builder(getContext())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle(R.string.alert_confirmation_delete_title)
                            .setMessage(R.string.alert_confirmation_delete_message)
                            .setPositiveButton(R.string.alert_confirmation_delete_yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SendBike sb = new SendBike(session.getUserId(), bike.getId(), null);
                                    _disposables.add(
                                            biketrackService.deleteBike(Statics.TOKEN_API, session.getToken(), sb)
                                                    .subscribeOn(Schedulers.newThread())
                                                    .observeOn(AndroidSchedulers.mainThread())
                                                    .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {

                                                        @Override
                                                        public void onComplete() {
                                                            Log.d(TAG, "DeleteBike completed");
                                                        }

                                                        @Override
                                                        public void onError(Throwable e) {
                                                            Log.e(TAG, "Error has occurred while deleting bike", e);
                                                        }

                                                        @Override
                                                        public void onNext(Response<ReceptAddBike> response) {
                                                            Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                                            closeFragment();
                                                        }
                                                    })
                                    );
                                }
                            })
                            .setNegativeButton(R.string.alert_confirmation_delete_no, null)
                            .show();
                }
                return true;
            }
        });


        _name.setText(bike.getBrand() + " " + bike.getName());
        mapView.onCreate(savedInstanceState);
        _bike_picture.setImageResource(R.drawable.ic_logo_black);
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
        getCoordinates();
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
                        if (i == 0){
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(sigfoxData.getLatitude()), Double.parseDouble(sigfoxData.getLongitude())))
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                            target = new LatLng(Double.parseDouble(sigfoxData.getLatitude()), Double.parseDouble(sigfoxData.getLongitude()));
                            _date_last_point.setText(sigfoxData.getTime());
                        } else {
                            googleMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(sigfoxData.getLatitude()), Double.parseDouble(sigfoxData.getLongitude()))));
                        }

                        i++;
                    }
                } catch (java.lang.NumberFormatException nfe){
                    Log.i(TAG, "Error ", nfe);
                }
            if (i > 10)
                break;
        }
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
//        if (ActivityCompat.checkSelfPermission(
//                getActivity(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(getActivity(),
//                        android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
//
//        }
//        googleMap.setMyLocationEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        MapsInitializer.initialize(this.getActivity());
        if (target == null)
            target = new LatLng(0.0,0.0);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(target, 15.0f);
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

    public void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }
}
