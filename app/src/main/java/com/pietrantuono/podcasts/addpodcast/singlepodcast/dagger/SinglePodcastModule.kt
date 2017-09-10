package com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.google.android.exoplayer2.SimpleExoPlayer
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModelImpl
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SinglePodcastPresenter
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.BitmapColorExtractor
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.playback.*
import com.pietrantuono.podcasts.player.player.player.Player
import com.pietrantuono.podcasts.player.player.service.BroadcastManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.IntentsManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificationCreator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificatorImpl
import com.pietrantuono.podcasts.repository.repository.Repository
import dagger.Module
import dagger.Provides

@Module
class SinglePodcastModule {
    private var activity: AppCompatActivity? = null

    constructor(activity: AppCompatActivity?) {
        this.activity = activity
    }

    constructor() {}

    @Provides
    fun provideSinglePodcastPresenter(factory: SinglePodcastPresenterFactoryFactory): SinglePodcastPresenter =
            ViewModelProviders.of(activity!!, factory).get(SinglePodcastPresenter::class.java)

    @Provides
    fun provideSinglePodcastModel(api: SinglePodcastApi, repository: Repository): SinglePodcastModel =
            SinglePodcastModelImpl(api, repository)

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper) = BitmapColorExtractor()

    @Provides
    fun providesPlayback(context: Context, exoplayer: SimpleExoPlayer, creator: PlaybackStateCreator): Playback = LocalPlayback(context, exoplayer, creator)

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper, player: Player?, creator: MediaSourceCreator) =
            SinglePodcastPresenterFactoryFactory(model, crashlyticsWrapper, player!!, creator)

    @Provides
    fun providePlaybackNotificator(logger: DebugLogger, creator: NotificationCreator, loader: SimpleImageLoader): PlaybackNotificator
            = PlaybackNotificatorImpl(logger, creator, loader)

    @Provides
    fun providesPlayerWrapper(localPlayback: Playback, creator: MediaSourceCreator): PlaybackWrapper = LocalPlaybackWrapper(localPlayback, creator)

    @Provides
    fun providesBroadcastManger(intentManager: IntentsManager) = BroadcastManager(intentManager)

    @Provides
    fun providesIntentManager(context: Context) = IntentsManager(context)

    @Provides
    fun providesPlaybackStateCreator() = PlaybackStateCreator()

    @Provides
    fun providesNotificationCreator(context: Context, loader: SimpleImageLoader, intentsmanager: IntentsManager) = NotificationCreator(context, loader, intentsmanager)
}

