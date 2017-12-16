package com.pietrantuono.podcasts.application

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloader.downloader.DownloaderImpl
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
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

    @Provides
    fun provideMediaBrowserCompatWrapper(context: Context) = MediaBrowserCompatWrapper(context)

    @Provides
    fun provideDownloader(preferencesManager: PreferencesManager, context: Context, executor: Executor, logger: DebugLogger): Downloader
            = DownloaderImpl(context, preferencesManager, executor, logger)

    @Provides
    fun provideExecutor(): Executor = Executors.newSingleThreadExecutor()

}

