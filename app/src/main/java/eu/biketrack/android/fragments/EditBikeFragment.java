package eu.biketrack.android.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.BrandSelected;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.models.data_send.SendBikeUpdate;
import eu.biketrack.android.session.Session;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class EditBikeFragment extends Fragment {
    private static String TAG = "BIKETRACK - EditBike";
    private Unbinder unbinder;
    private Session session;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private Bike _bike;
    private BrandSelected brandSelected;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bike_name_edit) EditText _name;
    @BindView(R.id.bike_trackerid_edit) EditText _trackerid;
    @BindView(R.id.bike_brand_edit) EditText _brand;
    @BindView(R.id.search_brand_button)
    Button _button_search_brand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        session = Session.getInstance();
        brandSelected = BrandSelected.getOurInstance();
        brandSelected.clear();
        Bundle bundle = getArguments();
        if (bundle != null)
            _bike = bundle.getParcelable("BIKE");
        else
            _bike = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_edit_bike, container, false);
        unbinder = ButterKnife.bind(this, layout);
        if (_bike == null)
            toolbar.setTitle(R.string.title_bike_new);
        else
            toolbar.setTitle(R.string.title_bike_edit);
        toolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.background, null));
        toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeFragment();
            }
        });
        if (_bike != null){
            _name.setText(_bike.getName());
            _brand.setText(_bike.getBrand());
            _trackerid.setText(_bike.getTracker());
        }
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (brandSelected.getBrand() != null)
            _brand.setText(brandSelected.getBrand());
    }

    @OnClick(R.id.bike_save_button)
    public void save_bike(){
        if (_bike == null) {
            SendBike sb = new SendBike(session.getUserId(), null, new SendBikeInfo(_name.getText().toString(), _brand.getText().toString(), _trackerid.getText().toString()));
            _disposables.add(
                    biketrackService.addBike(Statics.TOKEN_API, session.getToken(), sb)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {

                                @Override
                                public void onComplete() {
                                    Log.d(TAG, "Add Bike completed");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "Error has occurred while creating bike", e);
                                }

                                @Override
                                public void onNext(Response<ReceptAddBike> repreceptAddBike) {
                                    Toast.makeText(getActivity(), "Response : " + repreceptAddBike.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, repreceptAddBike.toString());
                                    if (repreceptAddBike.code() == 200){
                                        closeFragment();
                                    }
                                }
                            })
            );
        } else {
            SendBikeUpdate sb = new SendBikeUpdate(session.getUserId(), _bike.getId(), new SendBikeInfo(_name.getText().toString(), _brand.getText().toString(), _bike.getTracker()));
            _disposables.add(
                    biketrackService.updateBike(Statics.TOKEN_API, session.getToken(), sb)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {

                                @Override
                                public void onComplete() {
                                    Log.d(TAG, "Update Bike completed");
                                }

                                @Override
                                public void onError(Throwable e) {
                                    Log.e(TAG, "Error has occurred while creating bike", e);
                                }

                                @Override
                                public void onNext(Response<ReceptAddBike> repreceptAddBike) {
                                    Toast.makeText(getActivity(), "Response : " + repreceptAddBike.toString(), Toast.LENGTH_SHORT).show();
                                    Log.d(TAG, repreceptAddBike.toString());
                                    if (repreceptAddBike.code() == 200){
                                        closeFragment();
                                    }
                                }
                            })
            );
        }
    }

    public void closeFragment(){
        getActivity().getSupportFragmentManager().popBackStack();
    }

    @OnClick(R.id.search_brand_button)
    public void openSearchBrand(){
        Fragment fragment = new BrandFragment();
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }
}
