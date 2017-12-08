package com.pietrantuono.podcasts.application

import android.content.Context
import com.google.android.exoplayer2.DefaultLoadControl
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloader.downloader.DownloaderImpl
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper
import com.pietrantuono.podcasts.settings.PreferencesManager
import dagger.Module
import dagger.Provides
import repo.repository.EpisodesRepository
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
    fun provideDownloader(repo: EpisodesRepository, fetcher: Fetcher, preferencesManager: PreferencesManager): Downloader = DownloaderImpl(context, preferencesManager)


    @Provides
    fun provideMediaBrowserCompatWrapper(context: Context) = MediaBrowserCompatWrapper(context)

}

