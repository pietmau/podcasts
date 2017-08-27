package com.pietrantuono.podcasts.player.player.service.messenger

import android.os.IBinder
import android.os.Messenger
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.Player


class OutgoingMessenger : Player, NotificatorService {
    override var boundToFullScreen: Boolean?
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.
        set(value) {}

    override fun checkIfShouldNotify() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun playFeed(source: PodcastFeedSource) {

    }

    override fun playEpisode(episode: MediaSource) {

    }

    override fun setMediaSource(source: MediaSource) {

    }

    private val messenger: Messenger

    constructor(iBinder: IBinder?) {
        messenger = Messenger(iBinder)
    }
}