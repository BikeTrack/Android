package eu.biketrack.android.api_connection;

import java.util.List;

import eu.biketrack.android.models.SigfoxData;
import eu.biketrack.android.models.data_reception.ReceptUser;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by 42900 on 20/06/2017 for BikeTrack_Android.
 */

public interface LobwickService {
    @GET("sigfoxData.json")
    Observable<List<SigfoxData>> getSigfoxDatas();
}
