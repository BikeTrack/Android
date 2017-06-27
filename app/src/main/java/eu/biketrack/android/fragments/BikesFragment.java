package eu.biketrack.android.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import eu.biketrack.android.session.Session;
import eu.biketrack.android.activities.CustomListAdapter;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.api_connection.Statics;
import eu.biketrack.android.models.User;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_send.SendBike;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class BikesFragment extends Fragment {

    private static String TAG = "BIKETRACK - Bikes";

    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;
    private Unbinder unbinder;
    private ArrayList<Bike> bikeArrayList = new ArrayList<>();
    private User user;
    private CustomListAdapter adapter;
    private Session session;

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
        session = Session.getInstance();

        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
        adapter = new CustomListAdapter(this.getActivity(), bikeArrayList);
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
        registerForContextMenu(list);
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
        Bundle bundle = new Bundle();
        bundle.putParcelable("BIKE", bikeArrayList.get(position));
        Fragment fragment = new BikeFragment();
        fragment.setArguments(bundle);
        final String tag = fragment.getClass().toString();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(tag)
                .replace(android.R.id.content, fragment, tag)
                .commit();
    }


    @OnClick(R.id.floatin_add_bike)
    public void addBike() {
        Fragment fragment = new EditBikeFragment();
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
                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
                        .subscribeOn(Schedulers.newThread())
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
                                bikeArrayList.clear();
                                int i = 0;
                                int listsize = user.getBikes().size();
                                if (listsize == 0)
                                    pg_bar.setVisibility(View.GONE);
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
                biketrackService.getBike(Statics.TOKEN_API, session.getToken(), bikeid)
                        .subscribeOn(Schedulers.newThread())
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
                                bikeArrayList.add(receiveBike.getBike());
                                list.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                if (last)
                                    pg_bar.setVisibility(View.GONE);
                            }

                        })
        );
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle(R.string.title_context_menu);
        menu.add(0, 0, 0, R.string.edit_context_menu);
        menu.add(0, 1, 1, R.string.delete_context_menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int selected_item = (int) info.id;
        int selected_option= item.getItemId();

        if (selected_option == 0){
            Bundle bundle = new Bundle();
            bundle.putParcelable("BIKE", bikeArrayList.get(selected_item));
            Fragment fragment = new EditBikeFragment();
            fragment.setArguments(bundle);
            final String tag = fragment.getClass().toString();
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(tag)
                    .replace(android.R.id.content, fragment, tag)
                    .commit();
        } else if (selected_option == 1){
            new AlertDialog.Builder(this.getContext())
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle(R.string.alert_confirmation_delete_title)
                    .setMessage(R.string.alert_confirmation_delete_message)
                    .setPositiveButton(R.string.alert_confirmation_delete_yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SendBike sb = new SendBike(session.getUserId(), bikeArrayList.get(selected_item).getId(), null);
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
                                                    getUser();
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
}
