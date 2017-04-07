package eu.biketrack.android.api_connection;

import java.util.List;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import io.reactivex.Observable;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public interface BiketrackService {
    @POST("signup")
    Observable<SignupReception> createUser(@Header("Authorization") String token, @Body AuthUser user);

    @POST("authenticate")
    Observable<AuthenticateReception> connectUser(@Header("Authorization") String token, @Body AuthUser user);

    @GET("bikes")
    Observable<List<Bike>> getBikes(@Header("Authorization") String token);

    @POST("bikes")
    Observable<eu.biketrack.android.models.data_send.Bike> addBike(@Header("Authorization") String token, @Body eu.biketrack.android.models.data_send.Bike bike);
}
