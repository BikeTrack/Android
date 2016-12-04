package eu.biketrack.android.api_connection;

import android.util.Log;

import eu.biketrack.android.models.data_reception.UserConnection;
import eu.biketrack.android.models.data_reception.UserInscription;
import eu.biketrack.android.models.data_send.User;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class ApiConnect {

    private static String TAG = "BikeTrack - ApiConnect";
    private UserConnection uCo;
    private UserInscription uIn;

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
        userObservable.subscribeOn(Schedulers.newThread());
        userObservable.observeOn(AndroidSchedulers.mainThread());
        Subscriber<UserInscription> userInscriptionSubscriber = new Subscriber<UserInscription>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                    Log.e(TAG, "Http error code : " + code + " : " + response.message() , response);
                }
            }

            @Override
            public void onNext(UserInscription ui) {
                uIn = ui;
                Log.d(TAG, "userInscription : " + ui.isSucess() + " " + ui.getMessage());

            }
        };

        userObservable.subscribe(userInscriptionSubscriber);
        if (userInscriptionSubscriber.isUnsubscribed())
            userInscriptionSubscriber.unsubscribe();

    }

    public void signIn(User user){
        BiketrackService biketrackService = buildService(BiketrackService.class);
        Observable<UserConnection> userObservable = biketrackService.connectUser(user);
        userObservable.subscribeOn(Schedulers.newThread());
        userObservable.observeOn(AndroidSchedulers.mainThread());
        Subscriber<UserConnection> userConnectionSubscriber = new Subscriber<UserConnection>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted()");
            }

            @Override
            public void onError(Throwable e) {
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                    Log.e(TAG, "Http error code : " + code + " : " + response.message() , response);
                }
            }

            @Override
            public void onNext(UserConnection uc) {
                uCo = uc;
                Log.d(TAG, "userConnection : " + uc.isSuccess() + " " + uc.getToken());

            }
        };

        userObservable.subscribe(userConnectionSubscriber);
        if (userConnectionSubscriber.isUnsubscribed())
            userConnectionSubscriber.unsubscribe();
    }

    public UserConnection getuCo() {
        return uCo;
    }

    public void setuCo(UserConnection uCo) {
        this.uCo = uCo;
    }

    public UserInscription getuIn() {
        return uIn;
    }

    public void setuIn(UserInscription uIn) {
        this.uIn = uIn;
    }
}