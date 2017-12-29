package eu.biketrack.android.bike;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.bill.Bill;
import eu.biketrack.android.editbike.EditBike;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.Location;
import eu.biketrack.android.models.data_reception.Tracker;
import eu.biketrack.android.session.Session;

import static eu.biketrack.android.api_connection.Statics.BATTERY_CRITICAL;
import static eu.biketrack.android.api_connection.Statics.BATTERY_LOW;

public class BikeFragment extends Fragment implements OnMapReadyCallback {
    public static final String ARG_BIKE = "BIKE";
    private static final int REQUEST_LOCATION = 1;
    private static String TAG = "BIKETRACK - Bike";
    //    @BindView(R.id.toolbar)
//    Toolbar toolbar;
    @BindView(R.id.bike_picture)
    ImageView _bike_picture;
    @BindView(R.id.bike_name_tv)
    TextView _name;
    @BindView(R.id.map)
    MapView mapView;
    //    @BindView(R.id.date_last_point) TextView _date_last_point;
    @BindView(R.id.battery_percent)
    TextView _battery_percent;
    @BindView(R.id.battery)
    ImageView _battery;
    @BindView(R.id.goToBillButton)
    ImageButton goToBillButton;
    private Unbinder unbinder;
    private Session session;
    private Bike bike;
    private BikeTrackerList bikeTrackerList = BikeTrackerList.getInstance();
    private int position;
    // private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
    private Tracker tracker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
        Bundle bundle = getArguments();
//        session = Session.getInstance();
//        bike = bundle.getParcelable("BIKE");
        position = bundle.getInt(ARG_BIKE);
    }

    @OnClick(R.id.floatin_update_bike)
    public void updateBike() {
        Intent i = new Intent(getActivity(), EditBike.class);
        i.putExtra("BikeId", bikeTrackerList.getPair(position).first.getId());
        startActivity(i);
        getActivity().finish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bike, container, false);
        unbinder = ButterKnife.bind(this, layout);

        _name.setText(bikeTrackerList.getPair(position).first.getName());
        tracker = bikeTrackerList.getPair(position).second;

        try {
            byte[] decodedString = Base64.decode(bikeTrackerList.getBikeArrayList().get(position).first.getPicture(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            _bike_picture.setImageBitmap(decodedByte);
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e);
        }


        try {
            if (tracker == null) {
                _battery.setImageResource(R.drawable.ic_broken_link);
            } else if (tracker.getBattery() == null) {
                _battery.setImageResource(R.drawable.ic_broken_link);
            } else if (tracker.getBattery().size() == 0) {
                _battery.setImageResource(R.drawable.ic_broken_link);
            } else if (tracker.getBattery().get(tracker.getBattery().size() - 1) == null) {
                _battery.setImageResource(R.drawable.ic_broken_link);
            } else if (tracker.getBattery().get(tracker.getBattery().size() - 1).getPourcentage() < BATTERY_CRITICAL) {
                _battery.setImageResource(R.drawable.ic_battery_critical);
            } else if (tracker.getBattery().get(tracker.getBattery().size() - 1).getPourcentage() < BATTERY_LOW) {
                _battery.setImageResource(R.drawable.ic_battery_low);
            } else {
                _battery.setImageResource(R.drawable.ic_battery_full);
            }
            _battery_percent.setText(String.valueOf(tracker.getCurrentRoundedBatteryPercentage()));
        } catch (Exception e) {
            Log.e(TAG, "onCreateView: ", e);
        }
        if (mapView != null)
            mapView.getMapAsync(BikeFragment.this);


//        if (toolbar != null) {
//            toolbar.inflateMenu(R.menu.bike_menu);
//            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    closeFragment();
//                }
//            });
//            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    Log.d(TAG, item.toString());
//                    int selected_option = item.getItemId();
//
//                    if (selected_option == R.id.action_edit_bike) {
//                        Bundle bundle = new Bundle();
//                        bundle.putParcelable("BIKE", bike);
//                        Fragment fragment = new EditBike();
//                        fragment.setArguments(bundle);
//                        final String tag = fragment.getClass().toString();
//                        getActivity().getSupportFragmentManager()
//                                .beginTransaction()
//                                .addToBackStack(tag)
//                                .replace(android.R.id.content, fragment, tag)
//                                .commit();
//                    } else if (selected_option == R.id.action_delete_bike) {
//                        new AlertDialog.Builder(getContext())
//                                .setIcon(android.R.drawable.ic_dialog_alert)
//                                .setTitle(R.string.alert_confirmation_delete_title)
//                                .setMessage(R.string.alert_confirmation_delete_message)
//                                .setPositiveButton(R.string.alert_confirmation_delete_yes, new DialogInterface.OnClickListener() {
//                                    @Override
//                                    public void onClick(DialogInterface dialog, int which) {
//                                        SendBike sb = new SendBike(session.getUserId(), bike.getId(), null);
//                                        _disposables.add(
//                                                biketrackService.deleteBike(Statics.TOKEN_API, session.getToken(), sb)
//                                                        .subscribeOn(Schedulers.newThread())
//                                                        .observeOn(AndroidSchedulers.mainThread())
//                                                        .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {
//
//                                                            @Override
//                                                            public void onComplete() {
//                                                                Log.d(TAG, "DeleteBike completed");
//                                                            }
//
//                                                            @Override
//                                                            public void onError(Throwable e) {
//                                                                Log.e(TAG, "Error has occurred while deleting bike", e);
//                                                            }
//
//                                                            @Override
//                                                            public void onNext(Response<ReceptAddBike> response) {
//                                                                if (response.code() == 200){
//                                                                    Toast.makeText(getActivity(), R.string.bike_deleted, Toast.LENGTH_SHORT).show();
//                                                                    closeFragment();
//                                                                } else {
//                                                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                                                }
//                                                            }
//                                                        })
//                                        );
//                                    }
//                                })
//                                .setNegativeButton(R.string.alert_confirmation_delete_no, null)
//                                .show();
//                    }
//                    return true;
//                }
//            });
//        }
        if (mapView != null)
            mapView.onCreate(savedInstanceState);
//        getBike();
        return layout;
    }

    //
//    private void setDatas(){
//        if (_name != null)
//            _name.setText(bike.getBrand() + " " + bike.getName());
//        if (_bike_picture != null)
//            _bike_picture.setImageResource(R.drawable.ic_logo_black);
//
//    }
//
//    private void getBike() {
//        _disposables.add(
//                biketrackService.getBike(Statics.TOKEN_API, session.getToken(), bike.getId())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceiveBike>() {
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Bike completed");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred while getting bike info", e);
//                                //check error type and raise toast
//                                Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(ReceiveBike receiveBike) {
//                                bike = receiveBike.getBike();
//                                setDatas();
//                                getCoordinates();
//                            }
//                        })
//        );
//    }
//
    @Override
    public void onDestroyView() {
        if (mapView != null)
            mapView.onDestroy();
        super.onDestroyView();
        unbinder.unbind();
    }

    //
    @Override
    public void onResume() {
        if (mapView != null)
            mapView.onResume();
//        getCoordinates();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null)
            mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        if (mapView != null)
            mapView.onLowMemory();
        super.onLowMemory();
    }

    //
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng target = null;
        int i = 0;
//        int j = 0;
//        if (tracker.getLocations().size() > Statics.MAX_POINTS_DISPLAYED_ON_MAP){
//            i = tracker.getLocations().size() - Statics.MAX_POINTS_DISPLAYED_ON_MAP;
//        }
        try {
            for (Location l : tracker.getLocations()) {
//            if (j >= Statics.MAX_POINTS_DISPLAYED_ON_MAP)
//                break;
                if (l.getCoordinates().get(1) != null && l.getCoordinates().get(0) != null) {
                    if (i == tracker.getLocations().size() - 1) {
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(l.getCoordinates().get(1), l.getCoordinates().get(0)))
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        target = new LatLng(l.getCoordinates().get(1), l.getCoordinates().get(0));
//                    if (_date_last_point != null)
//                        _date_last_point.setText(l.getTimestamp());
                    } else {
                        googleMap.addMarker(new MarkerOptions()
                                .position(new LatLng(l.getCoordinates().get(1), l.getCoordinates().get(0))));
                    }
                }
                ++i;
//            ++j;
            }
        } catch (Exception e) {
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
            target = new LatLng(0.0, 0.0);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(target, 15.0f);
        googleMap.moveCamera(cameraUpdate);
    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_LOCATION: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // TODO: 20/06/2017 find a way to redraw the map or reload the fragment
//                }
//            }
//        }
//    }
//
//    public void getCoordinates(){
//        _disposables.add(
//                biketrackService.getTracker(Statics.TOKEN_API, session.getToken(), bike.getTracker())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceiveTracker>() {
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Login completed");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred while getting coordinates ", e);
//                                if (_battery != null){
//                                    _battery.setImageResource(R.drawable.ic_broken_link);
//                                }
//                            }
//
//                            @Override
//                            public void onNext(ReceiveTracker receiveTracker) {
//                                tracker = receiveTracker.getTracker();
//
//                                if (_battery == null){
//                                    return;
//                                } else if (tracker == null){
//                                    _battery.setImageResource(R.drawable.ic_broken_link);
//                                    return;
//                                } else if (tracker.getBattery() == null){
//                                    _battery.setImageResource(R.drawable.ic_broken_link);
//                                    return ;
//                                } else if (tracker.getBattery().size() == 0){
//                                    _battery.setImageResource(R.drawable.ic_broken_link);
//                                    return ;
//                                }else if (tracker.getBattery().get(tracker.getBattery().size() - 1) == null){
//                                    _battery.setImageResource(R.drawable.ic_broken_link);
//                                    return ;
//                                }
//
//                                if (tracker.getBattery().get(tracker.getBattery().size() - 1).getPourcentage() < BATTERY_CRITICAL){
//                                    _battery.setImageResource(R.drawable.ic_battery_critical);
//                                } else if (tracker.getBattery().get(tracker.getBattery().size() - 1).getPourcentage() < BATTERY_LOW){
//                                    _battery.setImageResource(R.drawable.ic_battery_low);
//                                } else {
//                                    _battery.setImageResource(R.drawable.ic_battery_full);
//                                }
//
//                                Log.d(TAG, tracker.getBattery().get(tracker.getBattery().size() - 1).getPourcentage().toString());
//
//                                if (mapView != null)
//                                    mapView.getMapAsync(BikeFragment.this);
//                            }
//                        })
//        );
//    }
//
//    public void closeFragment(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }

    @OnClick(R.id.goToBillButton)
    public void openBill() {
        Intent i = new Intent(getActivity(), Bill.class);
        i.putExtra("BikeId", bikeTrackerList.getPair(position).first.getId());
        startActivity(i);
        getActivity().finish();
    }
}
