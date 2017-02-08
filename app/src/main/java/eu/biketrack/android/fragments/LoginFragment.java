package eu.biketrack.android.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.OnClick;
import eu.biketrack.android.R;
import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import eu.biketrack.android.models.data_reception.UserConnection;
import eu.biketrack.android.models.data_send.User;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class LoginFragment extends Fragment {
    private ApiConnect apiConnect;
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        apiConnect = new ApiConnect();
        biketrackService = apiConnect.buildService(BiketrackService.class);
        _disposables = new CompositeDisposable();
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, layout);
        return layout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.email_log_in_button)
    public void login(){
        _disposables.add(
                biketrackService.connectUser(new User("didi", "azerty"))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<UserConnection>(){

                            @Override
                            public void onComplete() {
                                Log.d("BIKETRACK","Retrofit call 1 completed");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Timber.e(e, "woops we got an error while getting the list of contributors");
                            }

                            @Override
                            public void onNext(UserConnection userConnection) {
                                Log.d("BIKETRACK",userConnection.toString());
                            }
                        })
        );
    }
}
