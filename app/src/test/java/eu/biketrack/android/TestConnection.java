package eu.biketrack.android;

import junit.framework.Assert;

import org.junit.Test;

import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.models.data_send.User;

import static org.junit.Assert.*;

/**
 * Created by 42900 on 07/10/2016 for BikeTrack_Android.
 */

public class TestConnection {
    @Test
    public void validConnection() throws Exception {
        ApiConnect api = new ApiConnect();
        User user = new User("didi", "azerty");
        api.signIn(user);
        System.out.println(api.getuCo());
        Assert.assertTrue(api.getuCo().isSuccess());
    }

}