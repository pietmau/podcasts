package com.pietrantuono.podcasts.player.player.service

import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.Playback
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import javax.inject.Inject


class PlayerService : InstrumentedService(), Player {
    @Inject lateinit var playback: Playback
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var creator: MediaSourceCreator

    override fun playFeed(source: PodcastFeedSource) {
        playback.playAll(creator.createConcatenateMediaSource(source))
    }

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule())?.inject(this)
        logger.debug(TAG, "onCreate")
    }

    override fun onBind(intent: Intent?): IBinder? {
        logger.debug(TAG, "onBind")
        return PlayerServiceBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug(TAG, "onStartCommand")
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.debug(TAG, "onUnbind")
        return true
    }

    override fun onRebind(intent: Intent?) {
        logger.debug(TAG, "onRebind")
    }

    override fun onDestroy() {
        logger.debug(TAG, "onDestroy")
    }
}

