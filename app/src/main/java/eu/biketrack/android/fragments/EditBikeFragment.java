package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.login.widget.LoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditBikeFragment extends Fragment {
    private static String TAG = "BIKETRACK - EditBike";
    private Unbinder unbinder;
    private AuthenticateReception auth;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    //private String bikeId = null;

    @BindView(R.id.bike_name_edit) EditText _name;
    @BindView(R.id.bike_colour_edit) EditText _colour;
    @BindView(R.id.bike_brand_edit) EditText _brand;
    @BindView(R.id.bike_trackerid_edit) EditText _trackerid;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();

        Bundle bundle = getArguments();
        auth = bundle.getParcelable("AUTH");
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_edit_bike, container, false);
        unbinder = ButterKnife.bind(this, layout);

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

    @OnClick(R.id.bike_save_button)
    public void save_bike(){
        SendBike sb = new SendBike(auth.getUserId(), new SendBikeInfo(_name.getText().toString(), _colour.getText().toString(), _brand.getText().toString(), _trackerid.getText().toString()));
        Log.d(TAG, auth.toString());
        Log.d(TAG, sb.toString());
        _disposables.add(
                biketrackService.addBike(Statics.TOKEN_API, auth.getToken(), sb)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>(){

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Add Bike completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while creating bike", e);
//                                //check error type and raise toast
//                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
//                                    Toast.makeText(getActivity(), "Wrong password ?", Toast.LENGTH_SHORT).show();
//                                else if (e.getMessage().equals("HTTP 404 Not Found"))
//                                    Toast.makeText(getActivity(),"You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(Response<ReceptAddBike> repreceptAddBike) {
                                Toast.makeText(getActivity(), "Response : " + repreceptAddBike.toString(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, repreceptAddBike.toString());
                            }
                        })
        );
    }
}
