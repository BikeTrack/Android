package eu.biketrack.android;

import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.Callable;

import eu.biketrack.android.api_connection.ApiConnect;
import eu.biketrack.android.api_connection.BiketrackService;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 42900 on 20/02/2017 for BikeTrack_Android.
 */

@RunWith(AndroidJUnit4.class)
@SmallTest
public class LoginTest {
    private static String TAG = "Biketrack [TEST] Login";
    private BiketrackService biketrackService;
    private CompositeDisposable _disposables;

    @BeforeClass
    public static void setUp() throws Exception {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(new Function<Callable<Scheduler>, Scheduler>() {
            @Override
            public Scheduler apply(Callable<Scheduler> schedulerCallable) throws Exception {
                return Schedulers.trampoline();
            }
        });
    }

    @AfterClass
    public static void tearDown() {
        RxAndroidPlugins.reset();
    }

    @Before
    public void init() {
        biketrackService = ApiConnect.createService();
        _disposables = new CompositeDisposable();
    }

    @Test
    public void testLogin() {
//        User user = new User("didi", "azerty");
//
//        _disposables.add(
//                biketrackService.connectUser(user)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribeWith(new DisposableObserver<UserConnection>(){
//
//                            @Override
//                            public void onComplete() {
//                                assertTrue("TEST D UN TRUC ", false);
//                            }
//
//                            @Override
//                            public void onError(Throwable e) {
//                                Log.e(TAG, "Error has occurred during login", e);
//                                //check error type and raise toast
//                            }
//
//                            @Override
//                            public void onNext(UserConnection userCo) {
//                                userConnection = userCo;
//                                assertTrue("TEST D UN TRUC CHELOU", false);
//                            }
//                        })
//        );
//        assertTrue("Login connexion : ", userConnection.isSuccess());
    }

    @After
    public void end() {
        _disposables.dispose();

    }
}
