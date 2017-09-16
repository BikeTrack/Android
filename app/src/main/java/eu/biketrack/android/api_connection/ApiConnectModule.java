package eu.biketrack.android.api_connection;

import android.util.Log;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
@Module
public class ApiConnectModule {

    private static final String TAG = "ApiConnectModule";

    @Provides
    public OkHttpClient provideClient(){

        Log.d(TAG, "provideClient");
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        return new OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request.Builder requestBuilder = request.newBuilder()
                        .header("Authorization", Statics.TOKEN_API);
                request = requestBuilder.build();
                return chain.proceed(request);
            }
        }).build();
    }

    @Provides
    public Retrofit provideRetrofit(OkHttpClient client){
        Log.d(TAG, "provideRetrofit");
        return new Retrofit.Builder()
                .baseUrl(Statics.ROOT_API)
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Provides
    public BiketrackService provideApiService(){
        Log.d(TAG, "provideApiService: provideAPIService");
        return provideRetrofit(provideClient()).create(BiketrackService.class);
    }
}
