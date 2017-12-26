package eu.biketrack.android.bikes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.bike.BikeCollectionActivity;
import eu.biketrack.android.editbike.EditBike;
import eu.biketrack.android.root.App;
import eu.biketrack.android.settings.SettingsTab;

public class Bikes extends Activity implements BikesMVP.View{

    private static String TAG = "BIKETRACK - Bikes";

//    private BiketrackService biketrackService;
//    private CompositeDisposable _disposables;
//    private Unbinder unbinder;
//    private ArrayList<Pair<Bike, Tracker>> bikeArrayList = new ArrayList<>();
//    private User user;
//    private CustomListAdapter adapter;

    @BindView(R.id.listView_bikes)
    public RecyclerView mRecyclerView;
    public RecyclerView.Adapter mAdapter = null;
    public RecyclerView.LayoutManager mLayoutManager = null;

    @Inject
    BikesMVP.Presenter presenter;


    //    @BindView(R.id.listView_bikes)
//    ListView list;
//    @BindView(R.id.emptylist_txt)
//    TextView emptyText;
    @BindView(R.id.bikesSwipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.progressBarBikes)
    ProgressBar pg_bar;

//    @BindView(R.id.bottom_navigation)
//    BottomNavigationView bottomNavigationView;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((App) getApplication()).getComponent().inject(this);
        setContentView(R.layout.fragment_bikes);
        ButterKnife.bind(this);
        //registerForContextMenu(list);




        pg_bar.setVisibility(View.GONE);


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(
                new RecyclerViewItemClickListener(this.getApplicationContext(), new RecyclerViewItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent i = new Intent(getApplicationContext(), BikeCollectionActivity.class);
                        i.putExtra("position", position);
                        startActivity(i);
                    }
                })
        );


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.getBikes();

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.setView(this);
        presenter.clearBikes();
        presenter.getBikes();
//        if (list != null) {
//            list.setEmptyView(emptyText);
//            registerForContextMenu(list);
//        }
    }

    @Override
    public void displayBikes() {
//        adapter = new CustomListAdapter(this, BikeTrackerList.getInstance().getBikeArrayList());
//        list.setAdapter(adapter);
        if (mAdapter == null) {
            mAdapter = new RecyclerViewBikesAdapter();
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    public void setProgressBar(boolean visible){
        if (visible)
            swipeRefreshLayout.setRefreshing(true);
        else
            swipeRefreshLayout.setRefreshing(false);
    }

    @OnClick(R.id.floatin_add_bike)
    public void addBike(){
        Intent i = new Intent(this, EditBike.class);
        startActivity(i);
    }

    @OnClick(R.id.bikes_param_button)
    public void openParams(){
        Intent i = new Intent(this, SettingsTab.class);
        startActivity(i);
//        finish();
    }

//    @OnItemClick(R.id.listView_bikes)
//    public void selectBike(int position) {
//        Intent i = new Intent(this, BikeCollectionActivity.class);
//        i.putExtra("position", position);
//        startActivity(i);
//    }

    //    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View layout = inflater.inflate(R.layout.fragment_bikes, container, false);
//        unbinder = ButterKnife.bind(this, layout);
//        if (list != null)
//            list.setEmptyView(emptyText);
//        if (pg_bar != null)
//            pg_bar.setVisibility(View.GONE);
//        registerForContextMenu(list);
//        return layout;
//    }
//
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//        bottomNavigationView.getMenu().getItem(1).setChecked(true);
//        bottomNavigationView.getMenu().getItem(2).setChecked(false);
//        bottomNavigationView.setOnNavigationItemSelectedListener(
//                new BottomNavigationView.OnNavigationItemSelectedListener() {
//                    @Override
//                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                        bottomNavigationView.getMenu().getItem(0).setChecked(false);
//                        bottomNavigationView.getMenu().getItem(1).setChecked(false);
//                        bottomNavigationView.getMenu().getItem(2).setChecked(false);
//                        switch (item.getItemId()) {
//                            case R.id.action_settings:
//                                getActivity().getSupportFragmentManager().beginTransaction()
//                                        .replace(android.R.id.content, new SettingsFragment(), this.toString())
//                                        .commit();
//                                break;
//                            case R.id.action_bikes:
//
//                                break;
//                            case R.id.action_profile:
//                                getActivity().getSupportFragmentManager().beginTransaction()
//                                        .replace(android.R.id.content, new ProfileFragment(), this.toString())
//                                        .commit();
//                                break;
//                        }
//                        return true;
//                    }
//                });
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        unbinder.unbind();
//    }
//

//
//
//    @OnClick(R.id.floatin_add_bike)
//    public void addBike() {
//        Fragment fragment = new EditBike();
//        final String tag = fragment.getClass().toString();
//        getActivity().getSupportFragmentManager()
//                .beginTransaction()
//                .addToBackStack(tag)
//                .replace(android.R.id.content, fragment, tag)
//                .commit();
//    }
//
//    private void getUser() {
//        if (pg_bar != null)
//            pg_bar.setVisibility(View.VISIBLE);
//        _disposables.add(
//                biketrackService.getUser(Statics.TOKEN_API, session.getToken(), session.getUserId())
//                        .subscribeOn(Schedulers.newThread())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<ReceptUser>() {
//
//                            @Override
//                            public void onComplete() {
//                                Log.d(TAG, "Login completed");
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred while getting user info", e);
//                                //check error type and raise toast
//                                if (e.getMessage().equals("HTTP 401 Unauthorized"))
//                                    Toast.makeText(getActivity(), "Wrong user ?", Toast.LENGTH_SHORT).show();
//                                else if (e.getMessage().equals("HTTP 404 Not Found"))
//                                    Toast.makeText(getActivity(), "You are not in our database, you should create an account", Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(getActivity(), "Maybe an error somewhere : " + e.getMessage(), Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onNext(ReceptUser receptUser) {
//                                Log.d(TAG, receptUser.toString());
//                                user = receptUser.getUser();
//                                bikeArrayList.clear();
//                                int i = 0;
//                                int listsize = user.getBikes().size();
//                                if (listsize == 0) {
//                                    if (pg_bar != null)
//                                        pg_bar.setVisibility(View.GONE);
//                                    adapter.notifyDataSetChanged();
//                                }
//                                for (String s : user.getBikes()) {
//                                    getBike(s, i >= listsize - 1);
//                                    ++i;
//                                }
//                            }
//                        })
//        );
//    }
//
//    private void getBike(String bikeid, Boolean last) {
//        _disposables.add(
//                biketrackService.getBike(Statics.TOKEN_API, session.getToken(), bikeid)
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
//                                if (last) {
//                                    if (pg_bar != null)
//                                        pg_bar.setVisibility(View.GONE);
//                                }
//                            }
//
//                            @Override
//                            public void onNext(ReceiveBike receiveBike) {
//                                //bikeArrayList.add(receiveBike.getBike());
//                                getTracker(receiveBike.getBike(), last);
//                            }
//                        })
//        );
//    }
//
//    public void getTracker(Bike bike, Boolean last){
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
//                                bikeArrayList.add( new Pair<Bike, Tracker>(bike, null));
//                                adapter.notifyDataSetChanged();
//                                if (last){
//                                    if (pg_bar != null)
//                                        pg_bar.setVisibility(View.GONE);
//                                }
//                            }
//
//                            @Override
//                            public void onNext(ReceiveTracker receiveTracker) {
//                                bikeArrayList.add( new Pair<Bike, Tracker>(bike, receiveTracker.getTracker()));
//                                if (list != null)
//                                    list.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                                if (last){
//                                    if (pg_bar != null)
//                                        pg_bar.setVisibility(View.GONE);
//                                }
//                            }
//                        })
//        );
//    }
//
//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
//    {
//        super.onCreateContextMenu(menu, v, menuInfo);
//        MenuInflater inflater = this.getActivity().getMenuInflater();
//        inflater.inflate(R.menu.bike_menu, menu);
//        menu.setHeaderTitle(R.string.title_context_menu);
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item){
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
//        int selected_item = (int) info.id;
//        int selected_option= item.getItemId();
//
//        if (selected_option == R.id.action_edit_bike){
//            Bundle bundle = new Bundle();
//            bundle.putParcelable("BIKE", bikeArrayList.get(selected_item).first);
//            Fragment fragment = new EditBike();
//            fragment.setArguments(bundle);
//            final String tag = fragment.getClass().toString();
//            getActivity().getSupportFragmentManager()
//                    .beginTransaction()
//                    .addToBackStack(tag)
//                    .replace(android.R.id.content, fragment, tag)
//                    .commit();
//        } else if (selected_option == R.id.action_delete_bike){
//            new AlertDialog.Builder(this.getContext())
//                    .setIcon(android.R.drawable.ic_dialog_alert)
//                    .setTitle(R.string.alert_confirmation_delete_title)
//                    .setMessage(R.string.alert_confirmation_delete_message)
//                    .setPositiveButton(R.string.alert_confirmation_delete_yes, new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            SendBike sb = new SendBike(session.getUserId(), bikeArrayList.get(selected_item).first.getId(), null);
//                            _disposables.add(
//                                    biketrackService.deleteBike(Statics.TOKEN_API, session.getToken(), sb)
//                                            .subscribeOn(Schedulers.newThread())
//                                            .observeOn(AndroidSchedulers.mainThread())
//                                            .subscribeWith(new DisposableObserver<Response<ReceptAddBike>>() {
//
//                                                @Override
//                                                public void onComplete() {
//                                                    Log.d(TAG, "DeleteBike completed");
//                                                }
//
//                                                @Override
//                                                public void onError(Throwable e) {
//                                                    Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                                }
//
//                                                @Override
//                                                public void onNext(Response<ReceptAddBike> response) {
//                                                    if (response.code() == 200){
//                                                        Toast.makeText(getActivity(), R.string.bike_deleted, Toast.LENGTH_SHORT).show();
//                                                        getUser();
//                                                    } else {
//                                                        Toast.makeText(getActivity(), R.string.network_error, Toast.LENGTH_SHORT).show();
//                                                    }
//
//                                                }
//                                            })
//                            );
//                        }
//                    })
//                    .setNegativeButton(R.string.alert_confirmation_delete_no, null)
//                    .show();
//        }
//        return true;
//    }
}
