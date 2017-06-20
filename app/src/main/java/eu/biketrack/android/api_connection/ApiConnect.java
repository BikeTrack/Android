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


    /*
        For temporary API (Lobwick's server)
     */

    private Retrofit buildRetrofitLobwick(){
        return new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Statics.LOBWICK_API)
                .build();
    }

    private <T> T buildServiceLobwick(final Class<T> service){
        return buildRetrofitLobwick().create(service);
    }

    public static LobwickService createServiceLobwick(){
        ApiConnect apiConnect = new ApiConnect();
        return apiConnect.buildServiceLobwick(LobwickService.class);
    }
}
