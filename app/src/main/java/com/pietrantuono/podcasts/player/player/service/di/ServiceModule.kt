package com.pietrantuono.podcasts.player.player.service.di

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.player.player.service.DelayedStopHandler
import com.pietrantuono.podcasts.player.player.service.playback.LocalPlayback
import com.pietrantuono.podcasts.player.player.service.playback.Playback
import com.pietrantuono.podcasts.player.player.service.playbackmanager.PlaybackManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.*
import com.pietrantuono.podcasts.player.player.service.provider.MusicProviderSource
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProviderImpl
import com.pietrantuono.podcasts.player.player.service.queue.QueueManager
import com.pietrantuono.podcasts.player.player.service.queue.QueueManagerImpl
import com.pietrantuono.podcasts.repository.EpisodesRepository
import dagger.Module
import dagger.Provides


@ServiceScope
@Module
class ServiceModule constructor(val musicService: MusicService) {

    @ServiceScope
    @Provides
    fun provideMusicProviderSource() = MusicProviderSource()

    @ServiceScope
    @Provides
    fun providePodcastProvider(repository: EpisodesRepository): PodcastProvider = PodcastProviderImpl(repository)

    @ServiceScope
    @Provides
    fun provideLocalPlayback(context: Context, provider: PodcastProvider): Playback = LocalPlayback(context, provider)

    @ServiceScope
    @Provides
    fun provideQueueManager(epository: EpisodesRepository, podcastProvider: PodcastProvider, imageloder: SimpleImageLoader): QueueManager
            = QueueManagerImpl(epository, podcastProvider, musicService, imageloder)

    @ServiceScope
    @Provides
    fun providePlaybackManager(quemanager: QueueManager, playback: Playback) = PlaybackManager(musicService, quemanager, playback)

    @ServiceScope
    @Provides
    fun provideMediaSession(context: Context, playbackManager: PlaybackManager): MediaSessionCompat {
        val session = MediaSessionCompat(context, "MusicService")
        session.setCallback(playbackManager.mediaSessionCallback)
        session.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        val pi = PendingIntent.getActivity(context.applicationContext, 99 /*request code*/,
                Intent(context.applicationContext, FullscreenPlayActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        session.setSessionActivity(pi)
        session.setExtras(Bundle())
        musicService.sessionToken = session.sessionToken
        return session
    }

    @ServiceScope
    @Provides
    fun provideDelayedStopHandler() = DelayedStopHandler(musicService)

    @ServiceScope
    @Provides
    fun provideNotificationManager(creator: NotificationCreator,
                                   notificationmanager: NotificationManagerCompat): Notificator =
            Notificator(musicService, creator, notificationmanager)

    @ServiceScope
    @Provides
    fun provideNotificationManagerCompat(): NotificationManagerCompat {
        val notificationManager = NotificationManagerCompat.from(musicService)
        notificationManager.cancelAll()
        return notificationManager
    }

    @ServiceScope
    @Provides
    fun provideNotificationCreator(notificationmanager: NotificationManagerCompat, iconcahe: IconCache,
                                   intents: Intents): NotificationCreator =
            NotificationCreatorImpl(musicService, notificationmanager, iconcahe, intents)

}

