package eu.biketrack.android.api_connection;

import java.io.Serializable;
import java.util.List;

import eu.biketrack.android.models.data_reception.Bike;
import retrofit2.Retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class ApiConnect implements Serializable {

    private static String TAG = "BikeTrack - ApiConnect";
    private String token;
    private List<Bike> bikes;

    private Retrofit buildRetrofit(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Statics.ROOT_API)
                .build();
    }

    private <T> T buildService(final Class<T> service){
        return buildRetrofit().create(service);
    }

    public static BiketrackService createService(){
        ApiConnect apiConnect = new ApiConnect();
        return apiConnect.buildService(BiketrackService.class);
    }

/*
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
        Log.v(TAG, "TEST");
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
                token = uc.getToken();
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


    public void getBikes() throws NullPointerException{
        if (token == null)
            throw new NullPointerException("Token is null");
        BiketrackService biketrackService = buildService(BiketrackService.class);
        Observable<List<SendBikeInfo>> bikeObservable = biketrackService.getBikes(this.token);
        bikeObservable.subscribeOn(Schedulers.newThread());
        bikeObservable.observeOn(AndroidSchedulers.mainThread());
        Subscriber<List<SendBikeInfo>> bikeSubscriber = new Subscriber<List<SendBikeInfo>>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() bikes");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "ERROR ", e);
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                    Log.e(TAG, "Http error code : " + code + " : " + response.message() , response);
                }
            }

            @Override
            public void onNext(List<SendBikeInfo> bike) {
                bikes = bike;
                Log.d(TAG, "bikes" + bike.toString());

            }
        };
        bikeObservable.subscribe(bikeSubscriber);
        if (bikeSubscriber.isUnsubscribed())
            bikeSubscriber.unsubscribe();
    }

    public List<SendBikeInfo> getBike(){
        return bikes;
    }

    public void addBike(eu.biketrack.android.models.data_send.SendBikeInfo newbike) throws NullPointerException{
        if (token == null)
            throw new NullPointerException("Token is null");
        BiketrackService biketrackService = buildService(BiketrackService.class);
        Observable<eu.biketrack.android.models.data_send.SendBikeInfo> bikeObservable = biketrackService.addBike(this.token, newbike);
        bikeObservable.subscribeOn(Schedulers.newThread());
        bikeObservable.observeOn(AndroidSchedulers.mainThread());
        Subscriber<eu.biketrack.android.models.data_send.SendBikeInfo> bikeSubscriber = new Subscriber<eu.biketrack.android.models.data_send.SendBikeInfo>() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted() addBike");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "Add SendBikeInfo ERROR ", e);
                if (e instanceof HttpException) {
                    HttpException response = (HttpException)e;
                    int code = response.code();
                    Log.e(TAG, "Http error code : " + code + " : " + response.message() + " // " +response.getMessage() , response);
                }
            }

            @Override
            public void onNext(eu.biketrack.android.models.data_send.SendBikeInfo bike) {
                Log.d(TAG, "newbike" + bike.toString());

            }
        };
        bikeObservable.subscribe(bikeSubscriber);
        if (bikeSubscriber.isUnsubscribed())
            bikeSubscriber.unsubscribe();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    */
}
