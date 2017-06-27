package eu.biketrack.android.api_connection;

import java.util.List;

import eu.biketrack.android.models.SigfoxData;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by 42900 on 20/06/2017 for BikeTrack_Android.
 */

public interface LobwickService {
    @GET("sigfoxData.json")
    Observable<List<SigfoxData>> getSigfoxDatas();
}
