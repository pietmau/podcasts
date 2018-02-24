package com.pietrantuono.podcasts.media;


import android.content.Context;

import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

import java.io.IOException;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class MediaModule {
    private static final String USER_AGENT="user_agent";

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
        return new ExtractorMediaSource.EventListener() {
            @Override
            public void onLoadError(IOException error) {

            }
        };
    }

}
