package eu.biketrack.android.editbike;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.models.biketracker.BikeTrackerList;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeInfo;
import eu.biketrack.android.root.App;

public class EditBike extends Activity implements EditBikeMVP.View{
    private static final String TAG = "EditBike";
    private Bike tmp = null;
    @Inject
    public EditBikeMVP.Presenter presenter;

//    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bike_name_edit) EditText _name;
//    @BindView(R.id.bike_trackerid_edit) EditText _trackerid;
//    @BindView(R.id.bike_brand_edit) EditText _brand;
//    @BindView(R.id.search_brand_button) Button _button_search_brand;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.fragment_edit_bike);
        ButterKnife.bind(this);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        String bikeid = getIntent().getStringExtra("BikeId");
        if (bikeid != null && !bikeid.isEmpty()) {
            tmp = BikeTrackerList.getInstance().getBikeByBikeId(bikeid);
            _name.setText(tmp.getName());
        }

       // biketrackService = ApiConnectModule.createService();
//        _disposables = new CompositeDisposable();
//        session = Session.getInstance();
//        brandSelected = BrandSelected.getOurInstance();
//        brandSelected.clear();
//        Bundle bundle = getArguments();
//        if (bundle != null)
//            _bike = bundle.getParcelable("BIKE");
//        else
//            _bike = null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
    }

    @OnClick(R.id.bike_save_button)
    public void saveBike(){
        SendBikeInfo bike = new SendBikeInfo(_name.getText().toString(), "", "7462C");
        if (tmp == null)
            presenter.createBike(bike);
        else
            presenter.updateBike(tmp.getId(), bike);
    }

    @Override
    public void close() {
        this.finish();
    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater,
//                             @Nullable ViewGroup container,
//                             @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_edit_bike, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        if (toolbar != null) {
//            if (_bike == null)
//                toolbar.setTitle(R.string.title_bike_new);
//            else
//                toolbar.setTitle(R.string.title_bike_edit);
//            toolbar.setNavigationIcon(R.drawable.ic_close_white_24px);
//            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    closeFragment();
//                }
//            });
//        }
//        if (_bike != null){
//            if (_name != null)
//                _name.setText(_bike.getName());
//            if (_brand != null)
//                _brand.setText(_bike.getBrand());
//            if (_trackerid != null)
//                _trackerid.setText(_bike.getTracker());
//        }
//        return layout;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (brandSelected.getBrand() != null){
//            if (_brand != null)
//                _brand.setText(brandSelected.getBrand());
//        }
//    }
//
//    @OnClick(R.id.bike_save_button)
//    public void save_bike(){
//        if (_name.getText().length() == 0 && _brand.getText().length() == 0){
//            Toast.makeText(getContext(), R.string.bike_error_name_or_brand_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (_trackerid.getText().length() == 0) {
//            Toast.makeText(getContext(), R.string.bike_error_trackerid_empty, Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (_bike == null) {
//            SendBike sb = new SendBike(session.getUserId(), null, new SendBikeInfo(_name.getText().toString(), _brand.getText().toString(), _trackerid.getText().toString()));
//            _disposables.add(
//                    biketrackService.addBike(Statics.TOKEN_API, session.getToken(), sb)
//                            .subscribeOn(Schedulers.newThread())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {
//
//                                @Override
//                                public void onComplete() {
//                                    Log.d(TAG, "Add Bike completed");
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    Log.e(TAG, "Error has occurred while creating bike", e);
//                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                }
//
//                                @Override
//                                public void onNext(Response<ReceptAddBike> repreceptAddBike) {
//                                    Toast.makeText(getActivity(), R.string.bike_created, Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, repreceptAddBike.toString());
//                                    if (repreceptAddBike.code() == 200){
//                                        closeFragment();
//                                    } else {
//                                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            })
//            );
//        } else {
//            SendBikeUpdate sb = new SendBikeUpdate(session.getUserId(), _bike.getId(), new SendBikeInfo(_name.getText().toString(), _brand.getText().toString(), _trackerid.getText().toString()));
//            _disposables.add(
//                    biketrackService.updateBikeList(Statics.TOKEN_API, session.getToken(), sb)
//                            .subscribeOn(Schedulers.newThread())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {
//
//                                @Override
//                                public void onComplete() {
//                                    Log.d(TAG, "Update Bike completed");
//                                }
//
//                                @Override
//                                public void onError(Throwable e) {
//                                    Log.e(TAG, "Error has occurred while creating bike", e);
//                                }
//
//                                @Override
//                                public void onNext(Response<ReceptAddBike> repreceptAddBike) {
//                                    Toast.makeText(getActivity(), R.string.bike_updated, Toast.LENGTH_SHORT).show();
//                                    Log.d(TAG, repreceptAddBike.toString());
//                                    if (repreceptAddBike.code() == 200){
//                                        closeFragment();
//                                    } else {
//                                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            })
//            );
//        }
//    }
//
//    public void closeFragment(){
//        getActivity().getSupportFragmentManager().popBackStack();
//    }
//
//    @OnClick(R.id.search_brand_button)
//    public void openSearchBrand(){
//        Fragment fragment = new BrandFragment();
//        final String tag = fragment.getClass().toString();
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack(tag)
//                .replace(android.R.id.content, fragment, tag)
//                .commit();
//    }
}
