package com.pietrantuono.podcasts.player.player.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.Playback
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import javax.inject.Inject


class PlayerService : Service(), Player {
    @Inject lateinit var playback: Playback
    @Inject lateinit var creator: MediaSourceCreator

    override fun playFeed(source: PodcastFeedSource) {
        playback.playAll(creator.createConcatenateMediaSource(source))
    }

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule())?.inject(this)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return PlayerServiceBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }
}

