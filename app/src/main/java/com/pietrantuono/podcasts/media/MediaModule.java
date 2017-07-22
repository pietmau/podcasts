package com.pietrantuono.podcasts.media;


import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.pietrantuono.podcasts.player.player.MediaSourceCreator;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MediaModule {
    private static final String USER_AGENT="user_agent";

    @Provides
    MediaSourceCreator provideMediaSourceCreator(DataSource.Factory factory,
                                                 ExtractorMediaSource.EventListener logger) {
        return new MediaSourceCreator(factory, new Handler(Looper.myLooper()), logger);
    }

    @Provides
    DataSource.Factory provideDataSourceFactory(@Named(USER_AGENT) String userAgent, Context context) {
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        return new DefaultDataSourceFactory(context, defaultBandwidthMeter,
                new DefaultHttpDataSourceFactory(userAgent, defaultBandwidthMeter));
    }

    @Provides
    @Named(USER_AGENT)
    String getAgent() {
        return USER_AGENT;
    }

    @Provides
    ExtractorMediaSource.EventListener provideExtractorMediaSourceEventListener() {
        return error -> {
        };
    }

}
