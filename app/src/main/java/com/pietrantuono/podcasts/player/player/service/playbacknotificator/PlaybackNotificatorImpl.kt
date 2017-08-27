package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.service.NotificatorService

class PlaybackNotificatorImpl(private val logger: DebugLogger) : PlaybackNotificator {

    override fun shuldNotify(notificatorService: NotificatorService): Boolean {
        val should = notificatorService.boundToFullScreen
        logger.debug(PlaybackNotificatorImpl::class.java.simpleName, "shuldNotify = " + should)
        return true
    }
}