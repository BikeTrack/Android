package eu.biketrack.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;
import butterknife.Unbinder;
import eu.biketrack.android.R;
import eu.biketrack.android.activities.CustomListAdapter;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceptUser;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class BikesFragment extends Fragment {

    private static String TAG = "BIKETRACK - Bikes";

    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private AuthenticateReception auth;
    private Unbinder unbinder;
    private ArrayList<Bike> arrayList = new ArrayList<>();
    private User user;
    private CustomListAdapter adapter;

    @BindView(R.id.listView_bikes)
    ListView list;
    @BindView(R.id.emptylist_txt)
    TextView emptyText;
    @BindView(R.id.progressBarBikes)
    ProgressBar pg_bar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        Bundle bundle = getArguments();
        auth = bundle.getParcelable("AUTH");

        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        adapter = new CustomListAdapter(this.getActivity(), arrayList);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
        getUser();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View layout = inflater.inflate(R.layout.fragment_bikes, container, false);
        unbinder = ButterKnife.bind(this, layout);
        list.setEmptyView(emptyText);
        pg_bar.setVisibility(View.GONE);
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

    @OnItemClick(R.id.listView_bikes)
    public void selectBike(int position) {
        Log.d(TAG, "select bike position = " + position);
    }


    @OnClick(R.id.floatin_add_bike)
    public void addBike() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("AUTH", auth);
        Fragment fragment = new EditBikeFragment();
        fragment.setArguments(bundle);
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }

    private void getUser() {
        pg_bar.setVisibility(View.VISIBLE);
        _disposables.add(
                biketrackService.getUser(Statics.TOKEN_API, auth.getToken(), auth.getUserId())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ReceptUser>() {

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Login completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while getting user info", e);
                                //check error type and raise toast
                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
                                    Toast.makeText(getActivity(), "Wrong user ?", Toast.LENGTH_SHORT).show();
                                else if (e.getMessage().equals("HTTP 404 Not Found"))
                                    Toast.makeText(getActivity(), "You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(ReceptUser receptUser) {
                                Log.d(TAG, receptUser.toString());
                                user = receptUser.getUser();
                                arrayList.clear();
                                int i = 0;
                                int listsize = user.getBikes().size();
                                for (String s : user.getBikes()) {
                                    getBike(s, i >= listsize - 1);
                                    ++i;
                                }
                            }
                        })
        );
    }

    private void getBike(String bikeid, Boolean last) {
        _disposables.add(
                biketrackService.getBike(Statics.TOKEN_API, auth.getToken(), bikeid)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<ReceiveBike>() {

                            @Override
                            public void onComplete() {
                                Log.d(TAG, "Bike completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e(TAG, "Error has occurred while getting bike info", e);
                                //check error type and raise toast
                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
                                    Toast.makeText(getActivity(), "Wrong user ?", Toast.LENGTH_SHORT).show();
                                else if (e.getMessage().equals("HTTP 404 Not Found"))
                                    Toast.makeText(getActivity(), "You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
                                else
                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNext(ReceiveBike receiveBike) {
                                arrayList.add(receiveBike.getBike());
                                list.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                if (last)
                                    pg_bar.setVisibility(View.GONE);
                            }

                        })
        );
    }


}
