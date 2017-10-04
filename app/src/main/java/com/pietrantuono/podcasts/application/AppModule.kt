package com.pietrantuono.podcasts.application

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Singleton
@Module
class AppModule(private val context: Context) {

    @Provides
    internal fun providesContext(): Context {
        return context
    }

    @Singleton
    @Provides
    internal fun provideExoPlayer(): SimpleExoPlayer {
        return ExoPlayerFactory.newSimpleInstance(
                context.applicationContext, DefaultTrackSelector(), DefaultLoadControl())
    }

}
