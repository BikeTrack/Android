package eu.biketrack.android.api_connection;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.ReceptUserUpdate;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.models.data_send.DeleteUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeUpdate;
import eu.biketrack.android.models.data_send.SendUserUpdate;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import io.reactivex.Observable;
import retrofit2.http.Path;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public interface BiketrackService {
    @POST("signup")
    Observable<SignupReception> createUser(@Header("Authorization") String token, @Body AuthUser user);

    @POST("authenticate")
    Observable<AuthenticateReception> connectUser(@Header("Authorization") String token, @Body AuthUser user);

    @GET("profile/{userID}")
    Observable<ReceptUser> getUser(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Path("userID") String userid);

    @PATCH("profile")
    Observable<Response<ReceptUserUpdate>> updateUser(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body SendUserUpdate sendUserUpdate);

    @HTTP(method = "DELETE", path = "profile", hasBody = true)
    Observable<Response<ReceptDeleteUser>> deleteUser(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body DeleteUser user);

    @POST("bike")
    Observable<Response<ReceptAddBike>> addBike(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body SendBike bike);

    @PATCH("bike")
    Observable<Response<ReceptAddBike>> updateBike(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body SendBikeUpdate bike);

    @HTTP(method = "DELETE", path = "bike", hasBody = true)
    Observable<Response<ReceptAddBike>> deleteBike(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body SendBike bike);

    @GET("bike/{bikeID}")
    Observable<ReceiveBike> getBike(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Path("bikeID") String bikeId);

//    @GET("tracker/{trackerId}")
//    Observable<ReceiveTracker> getTracker(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Path("trackerId") String trackerId);
}
