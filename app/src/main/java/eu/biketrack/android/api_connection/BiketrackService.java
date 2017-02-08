package eu.biketrack.android.api_connection;

import java.util.List;

import eu.biketrack.android.models.data_reception.Bike;
import eu.biketrack.android.models.data_reception.UserConnection;
import eu.biketrack.android.models.data_reception.UserInscription;
import eu.biketrack.android.models.data_send.User;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import io.reactivex.Observable;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public interface BiketrackService {
    @POST("users")
    Observable<UserInscription> createUser(@Body User user);

    @POST("users/login")
    Observable<UserConnection> connectUser(@Body User user);

    @GET("bikes")
    Observable<List<Bike>> getBikes(@Header("Authorization") String token);

    @POST("bikes")
    Observable<eu.biketrack.android.models.data_send.Bike> addBike(@Header("Authorization") String token, @Body eu.biketrack.android.models.data_send.Bike bike);
}
