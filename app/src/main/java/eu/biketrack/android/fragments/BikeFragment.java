package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.Bike;
import io.reactivex.disposables.CompositeDisposable;


public class BikeFragment extends Fragment {
    private static String TAG = "BIKETRACK - SendBikeInfo";
    private Unbinder unbinder;
    private AuthenticateReception auth;
    private Bike bike;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;

    @BindView(R.id.bike_name_tv) TextView _name;
    @BindView(R.id.bike_colour_tv) TextView _colour;
    @BindView(R.id.bike_brand_tv) TextView _brand;
    @BindView(R.id.bike_trackerid_tv) TextView _trackerid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();

        Bundle bundle = getArguments();
        auth = bundle.getParcelable("AUTH");
        bike = bundle.getParcelable("BIKE");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_bike, container, false);
        unbinder = ButterKnife.bind(this, layout);

        _name.setText(bike.getName());
        _colour.setText(bike.getColor());
        _brand.setText(bike.getBrand());
        _trackerid.setText(bike.getTracker());
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
