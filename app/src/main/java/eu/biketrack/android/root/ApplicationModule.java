package eu.biketrack.android.root;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import eu.biketrack.android.session.Session;

/**
 * Created by 42900 on 10/07/2017 for BikeTrack_Android.
 */

@Module
public class ApplicationModule {

    private Application application;
    private Session session;

    public ApplicationModule(Application application) {
        this.application = application;
        if (session == null) this.session = new Session();
    }

    public void SessionModule(Session session){this.session = session;}

    @Provides
    @Singleton
    public Context provideContext(){
        return application;
    }

    @Provides
    @Singleton
    public Session provideSession(){return session;}

}
