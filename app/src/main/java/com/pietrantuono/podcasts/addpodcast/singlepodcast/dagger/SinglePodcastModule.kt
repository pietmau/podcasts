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
import com.pietrantuono.podcasts.main.view.TransitionsHelper
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.playback.LocalPlayback
import com.pietrantuono.podcasts.player.player.playback.LocalPlaybackWrapper
import com.pietrantuono.podcasts.player.player.playback.Playback
import com.pietrantuono.podcasts.player.player.service.BroadcastManager
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.*
import com.pietrantuono.podcasts.repository.repository.Repository
import dagger.Module
import dagger.Provides
import javax.inject.Named

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
    fun providesPlayback(context: Context, exoplayer: SimpleExoPlayer): Playback = LocalPlayback(context, exoplayer)

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper, player: Player?, creator: MediaSourceCreator) =
            SinglePodcastPresenterFactoryFactory(model, crashlyticsWrapper, player!!, creator)

    @Provides
    fun providePlaybackNotificator(logger: DebugLogger, context: Context, intentManager: IntentManager): PlaybackNotificator
            = PlaybackNotificatorImpl(logger, NotificationCreator(context, AlbumArtCache(), intentManager))

    @Provides
    @Named(LocalPlaybackWrapper.TAG)
    fun providesPlayerWrapper(localPlayback: Playback, creator: MediaSourceCreator): Player = LocalPlaybackWrapper(localPlayback, creator)


    @Provides
    fun providesBroadcastManger(intentManager: IntentManager) = BroadcastManager(intentManager)

    @Provides
    fun providesIntentManager() = IntentManager()

}

