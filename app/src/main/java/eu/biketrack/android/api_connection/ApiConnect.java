package eu.biketrack.android.api_connection;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.Serializable;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public class ApiConnect implements Serializable {

    private static String TAG = "BikeTrack - ApiConnect";


    /*
        For BikeTrack API
     */
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
}
