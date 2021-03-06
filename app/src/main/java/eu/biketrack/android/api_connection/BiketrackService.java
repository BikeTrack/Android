package eu.biketrack.android.api_connection;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeUpdate;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import rx.Observable;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public interface BiketrackService {
    @POST("signup")
    Observable<SignupReception> createUser(@Body AuthUser user);

    @POST("authenticate")
    Observable<AuthenticateReception> connectUser(@Body AuthUser user);

    @GET("profile/{userID}")
    Observable<ReceptUser> getUser(@Header("x-access-token") String token, @Path("userID") String userid);
//
//    @PATCH("profile")
//    Observable<Response<ReceptUserUpdate>> updateUser(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body SendUserUpdate sendUserUpdate);
//
//    @Multipart
//    @PATCH("profile")
//    Observable<ReceptUserUpdate> uploadProfilePhoto(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Part MultipartBody.Part image, @Part("contentType") String contentType);
//
//    @HTTP(method = "DELETE", path = "profile", hasBody = true)
//    Observable<Response<ReceptDeleteUser>> deleteUser(@Header("Authorization") String token, @Header("x-access-token") String access_token, @Body DeleteUser user);
//
    @POST("bike")
    Observable<ReceptAddBike> addBike(@Header("x-access-token") String access_token, @Body SendBike bike);

    @PATCH("bike")
    Observable<ReceptAddBike> updateBike(@Header("x-access-token") String access_token, @Body SendBikeUpdate bike);

    @HTTP(method = "DELETE", path = "bike", hasBody = true)
    Observable<ReceptAddBike> deleteBike(@Header("x-access-token") String access_token, @Body SendBike bike);

    @GET("bike/{bikeID}")
    Observable<ReceiveBike> getBike(@Path("bikeID") String bikeId, @Header("x-access-token") String access_token);

    @GET("tracker/{trackerId}")
    Observable<ReceiveTracker> getTracker(@Path("trackerId") String trackerId, @Header("x-access-token") String access_token);

    @Streaming
    @GET("testPicture")
    Observable<String> getTestPicture(@Header("x-access-token") String access_token);

    @Streaming
    @GET("bikeGetPicture/{bikeId}")
    Observable<String> getBikePicture(@Header("x-access-token") String access_token, @Path("bikeId") String biekId);

    @POST("bikePostPicture")
    @Multipart
    Observable<ReceiveBike> uploadBikePicture(@Header("x-access-token") String access_token, @Part("bikeId") String bikeId, @Part MultipartBody.Part photo);
}
