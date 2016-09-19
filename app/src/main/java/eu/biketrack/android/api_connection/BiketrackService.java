package eu.biketrack.android.api_connection;

import eu.biketrack.android.models.data_reception.UserConnection;
import eu.biketrack.android.models.data_reception.UserInscription;
import eu.biketrack.android.models.data_send.User;
import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by adrienschricke on 17/09/2016 for Android.
 */
public interface BiketrackService {
    @POST("users")
    Observable<UserInscription> createUser(@Body User user);

    @POST("users/login")
    Observable<UserConnection> connectUser(@Body User user);
}
