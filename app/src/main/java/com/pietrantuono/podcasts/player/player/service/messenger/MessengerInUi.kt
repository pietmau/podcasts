package com.pietrantuono.podcasts.player.player.service.messenger

import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import com.pietrantuono.podcasts.player.player.service.Player


class MessengerInUi : Player, NotificatorService {
    private val messenger: Messenger
    private val crashlytics: CrashlyticsWrapper = CrashlyticsWrapper()

    override var boundToFullScreen: Boolean?
        get() = throw UnsupportedOperationException("Unsupported")
        set(value) {}

    constructor(iBinder: IBinder?) {
        messenger = Messenger(iBinder)
    }

    override fun checkIfShouldNotify() {
        send(Message.obtain(null, HandlerInService.CHECK_SHOULD_NOTIFY))
    }

    override fun playFeed(source: PodcastFeedSource) {
        throw UnsupportedOperationException("Unsupported")
    }

    override fun playEpisode(episode: MediaSource) {
        throw UnsupportedOperationException("Unsupported")
    }

    override fun setEpisode(episode: Episode) {
        val msg = Message.obtain(null, HandlerInService.SET_EPISODE, episode)
        send(msg)
    }

    private fun send(msg: Message?) {
        try {
            messenger.send(msg)
        } catch (exception: Exception) {
            crashlytics.logException(exception)
        }

    }
}