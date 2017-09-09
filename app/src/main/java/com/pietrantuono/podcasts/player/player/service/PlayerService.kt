package com.pietrantuono.podcasts.player.player.service

import android.content.Intent
import android.os.IBinder
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.playback.LocalPlaybackWrapper
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import javax.inject.Inject
import javax.inject.Named

class PlayerService : InstrumentedService(), Player, NotificatorService {
    override var boundToFullScreen: Boolean = false
        set(value) {
            field = value
            checkIfShoudBeForeground()
        }

    private val listeners: Set<Listener> = setOf()
    @field:[Inject Named(LocalPlaybackWrapper.TAG)] lateinit var playback: Player
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var creator: MediaSourceCreator
    @Inject lateinit var resources: ResourcesProvider
    @Inject lateinit var notificator: PlaybackNotificator

    override fun playFeed(source: PodcastFeedSource) {
        //playback.playAll(creator.createConcatenateMediaSource(source))
    }

    override fun setEpisode(episode: Episode) {
        playback.setEpisode(episode)
    }

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule())?.inject(this)
        logger.debug(TAG, "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        logger.debug(TAG, "onBind")
        checkIfShoudBeForeground()
        return PlayerServiceBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug(TAG, "onStartCommand")
        checkIfShoudBeForeground()
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.debug(TAG, "onUnbind")
        checkIfShoudBeForeground()
        return true
    }

    override fun onRebind(intent: Intent?) {
        logger.debug(TAG, "onRebind")
        checkIfShoudBeForeground()
    }

    override fun onDestroy() {
        checkIfShoudBeForeground()
        logger.debug(TAG, "onDestroy")
    }

    override fun onTrimMemory(level: Int) {
        logger.debug(TAG, "onTrimMemory")
    }

    override fun playEpisode(episode: MediaSource) {
        //playback.playMediaSource(episode)
    }

    interface Listener {
        fun onError(message: String?)
    }

    override fun checkIfShoudBeForeground() {
        notificator.checkIfShoudBeForeground(this)
    }
}

