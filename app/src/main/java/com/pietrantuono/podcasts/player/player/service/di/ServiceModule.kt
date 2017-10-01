package com.pietrantuono.podcasts.player.player.service.di

import android.content.Context
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.playback.LocalPlaybackWrapper
import com.pietrantuono.podcasts.player.player.playback.Playback
import com.pietrantuono.podcasts.player.player.playback.PlaybackWrapper
import com.pietrantuono.podcasts.player.player.service.BroadcastManager
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModel
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModelImpl
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.IntentsManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificationCreator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificatorImpl
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProviderImpl
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides

@Module
class ServiceModule {

    @Provides
    fun providesPlayerWrapper(localPlayback: Playback, creator: MediaSourceCreator): PlaybackWrapper = LocalPlaybackWrapper(localPlayback, creator)

    @Provides
    fun providePlaybackNotificator(logger: DebugLogger, creator: NotificationCreator, loader: SimpleImageLoader): PlaybackNotificator
            = PlaybackNotificatorImpl(logger, creator, loader)

    @Provides
    fun providesBroadcastManger(intentManager: IntentsManager) = BroadcastManager(intentManager)

    @Provides
    fun providesNotificationCreator(context: Context, loader: SimpleImageLoader, intentsmanager: IntentsManager) = NotificationCreator(context, loader, intentsmanager)

    @Provides
    fun providesModel(repo: EpisodesRepository): PlayerServiceModel = PlayerServiceModelImpl(repo)

    @Provides
    fun providesIntentManager(context: Context) = IntentsManager(context)

    @Provides
    fun provideMusicProviderSource() = CustomMusicProviderSource()

    @Provides
    fun providePodcastProvider(repository: EpisodesRepository): PodcastProvider = PodcastProviderImpl(repository)

}

