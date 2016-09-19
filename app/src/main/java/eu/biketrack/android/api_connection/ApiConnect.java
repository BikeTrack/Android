package eu.biketrack.android.api_connection;

import android.util.Log;

import eu.biketrack.android.models.data_reception.UserConnection;
import eu.biketrack.android.models.data_reception.UserInscription;
import eu.biketrack.android.models.data_send.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class ApiConnect {

    private Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Statics.ROOT_API)
                .build();
    }

    private <T> T buildService(final Class<T> service){
        return buildRetrofit().create(service);
    }

    public void signUp(User user){
        BiketrackService biketrackService = buildService(BiketrackService.class);
        Observable<UserInscription> userObservable = biketrackService.createUser(user);
        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userInscription -> userInscription.isSucess() + " and " + userInscription.getMessage())
                .subscribe(userInfo -> Log.d("Output", userInfo));
    }

    public void signIn(User user){
        BiketrackService biketrackService = buildService(BiketrackService.class);
        Observable<UserConnection> userObservable = biketrackService.connectUser(user);
        userObservable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(userInscription -> userInscription.isSuccess() + " and " + userInscription.getToken())
                .subscribe(userInfo -> Log.d("Output", userInfo));
    }
}
