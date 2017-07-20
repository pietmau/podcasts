package com.pietrantuono.podcasts.application;

import android.content.Context;
import android.support.annotation.Nullable;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.pietrantuono.podcasts.player.player.service.Player;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.RealmRepository;
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.Repository;

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
    Context providesContext() {
        return context;
    }

    @Provides
    Repository provideRepository() {
        return new RealmRepository();
    }

    @Singleton
    @Provides
    SimpleExoPlayer provideExoPlayer() {
        return ExoPlayerFactory.newSimpleInstance(
                context, new DefaultTrackSelector(), new DefaultLoadControl());
    }

    @Nullable
    @Provides
    Player providePlayer() {
        return ((App) context).getPlayer();
    }

}
