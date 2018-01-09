package eu.biketrack.android.api_connection;

import eu.biketrack.android.models.data_reception.AuthenticateReception;
import eu.biketrack.android.models.data_reception.ReceiveBike;
import eu.biketrack.android.models.data_reception.ReceiveTracker;
import eu.biketrack.android.models.data_reception.ReceptAddBike;
import eu.biketrack.android.models.data_reception.ReceptDeleteUser;
import eu.biketrack.android.models.data_reception.ReceptUser;
import eu.biketrack.android.models.data_reception.ReceptUserUpdate;
import eu.biketrack.android.models.data_reception.SignupReception;
import eu.biketrack.android.models.data_send.AuthUser;
import eu.biketrack.android.models.data_send.AuthUserFB;
import eu.biketrack.android.models.data_send.CancelEmergency;
import eu.biketrack.android.models.data_send.DeleteUser;
import eu.biketrack.android.models.data_send.SendBike;
import eu.biketrack.android.models.data_send.SendBikeUpdate;
import eu.biketrack.android.models.data_send.SendUserTokenAndroid;
import eu.biketrack.android.models.data_send.SendUserUpdate;
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
    @POST("signupFb")
    Observable<SignupReception> createUserFB(@Body AuthUserFB user);

    @POST("signup")
    Observable<SignupReception> createUser(@Body AuthUser user);

    @POST("authenticate")
    Observable<AuthenticateReception> connectUser(@Body AuthUser user);

    @GET("profile/{userID}")
    Observable<ReceptUser> getUser(@Header("x-access-token") String token, @Path("userID") String userid);

    @PATCH("profile")
    Observable<ReceptUserUpdate> updateUser(@Header("x-access-token") String access_token, @Body SendUserUpdate sendUserUpdate);

    @PATCH("profile")
    Observable<ReceptUserUpdate> updateUserToken(@Header("x-access-token") String access_token, @Body SendUserTokenAndroid sendUserUpdate);

    @HTTP(method = "DELETE", path = "profile", hasBody = true)
    Observable<ReceptDeleteUser> deleteUser(@Header("x-access-token") String access_token, @Body DeleteUser user);

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
    Observable<String> getBikePicture(@Header("x-access-token") String access_token, @Path("bikeId") String bikeId);

    @POST("bikePostPicture")
    @Multipart
    Observable<ReceiveBike> uploadBikePicture(@Header("x-access-token") String access_token, @Part("bikeId") String bikeId, @Part MultipartBody.Part photo);

    @Streaming
    @GET("bikeGetBill/{bikeId}")
    Observable<String> getBikeBill(@Header("x-access-token") String access_token, @Path("bikeId") String bikeId);

    @POST("bikePostBill")
    @Multipart
    Observable<ReceiveBike> uploadBikeBill(@Header("x-access-token") String access_token, @Part("bikeId") String bikeId, @Part MultipartBody.Part photo);

    @POST("stillAlive")
    Observable<SignupReception> cancelEmergency(@Body CancelEmergency cancelEmergency);
}
