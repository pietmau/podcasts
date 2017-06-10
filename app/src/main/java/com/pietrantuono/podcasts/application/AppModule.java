package com.pietrantuono.podcasts.application;

import android.content.Context;

import com.pietrantuono.podcasts.singlepodcast.model.RealmRepository;
import com.pietrantuono.podcasts.singlepodcast.model.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Singleton
@Module
public class AppModule {
    private final Context context;

    public AppModule(Context context) {
        this.context = context;
    }

    @Provides
    Context providesContext(){
        return context;
    }

    @Provides
    Repository provideRepository(){
        return new RealmRepository();
    }

}
