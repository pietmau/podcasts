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
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificatorImpl
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
    fun provideSinglePodcastPresenter(factory: SinglePodcastPresenterFactoryFactory): SinglePodcastPresenter {
        return ViewModelProviders.of(activity!!, factory).get(SinglePodcastPresenter::class.java)
    }

    @Provides
    fun provideSinglePodcastModel(api: SinglePodcastApi, repository: Repository): SinglePodcastModel {
        return SinglePodcastModelImpl(api, repository)
    }

    @Provides
    fun provideTransitionImageLoadingListener(framework: TransitionsHelper): BitmapColorExtractor {
        return BitmapColorExtractor()
    }

    @Provides
    fun providesPlayback(context: Context, exoplayer: SimpleExoPlayer): Playback {
        return LocalPlayback(context, exoplayer)
    }

    @Provides
    fun provideSinglePodcastPresenterFactory(model: SinglePodcastModel, crashlyticsWrapper: CrashlyticsWrapper, player: Player?, creator: MediaSourceCreator): SinglePodcastPresenterFactoryFactory {
        return SinglePodcastPresenterFactoryFactory(model, crashlyticsWrapper, player!!, creator)
    }

    @Provides
    fun providePlaybackNotificator(logger: DebugLogger): PlaybackNotificator {
        return PlaybackNotificatorImpl(logger)
    }

    @Provides
    @Named(LocalPlaybackWrapper.TAG)
    fun providesPlayerWrapper(localPlayback: Playback, creator: MediaSourceCreator): Player {
        return LocalPlaybackWrapper(localPlayback, creator)
    }
}

