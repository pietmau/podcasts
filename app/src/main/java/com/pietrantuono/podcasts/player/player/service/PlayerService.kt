package com.pietrantuono.podcasts.player.player.service

import android.content.Intent
import android.os.IBinder
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.Playback
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.service.messenger.IncomingMessenger
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import javax.inject.Inject


class PlayerService : InstrumentedService(), Player, NotificatorService {
    override var boundToFullScreen: Boolean? = false
    private val listeners: Set<Listener> = setOf()
    @Inject lateinit var playback: Playback
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var creator: MediaSourceCreator
    @Inject lateinit var resources: ResourcesProvider
    @Inject lateinit var notificator: PlaybackNotificator
    private var incomingMessenger: IncomingMessenger? = null

    override fun playFeed(source: PodcastFeedSource) {
        playback.playAll(creator.createConcatenateMediaSource(source))
    }

    override fun setMediaSource(source: MediaSource) {
        playback.setMediaSource(source)
    }

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule())?.inject(this)
        incomingMessenger = IncomingMessenger(this)
        logger.debug(TAG, "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        logger.debug(TAG, "onBind")
        checkIfShouldNotify()
        return incomingMessenger?.iBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug(TAG, "onStartCommand")
        checkIfShouldNotify()
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.debug(TAG, "onUnbind")
        checkIfShouldNotify()
        return true
    }


    override fun onRebind(intent: Intent?) {
        logger.debug(TAG, "onRebind")
        checkIfShouldNotify()
    }

    override fun onDestroy() {
        logger.debug(TAG, "onDestroy")
    }

    override fun playEpisode(episode: MediaSource) {
        playback.playMediaSource(episode)
    }

    private fun onError(message: String?) {
        listeners.forEach { it.onError(message) }
    }

    interface Listener {
        fun onError(message: String?)
    }

    override fun checkIfShouldNotify() {
        notificator.shuldNotify(this)
    }

}

