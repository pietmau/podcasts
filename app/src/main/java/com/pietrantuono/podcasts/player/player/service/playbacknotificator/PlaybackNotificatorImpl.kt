package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.service.NotificatorService

class PlaybackNotificatorImpl(private val logger: DebugLogger,
                              private val notificationCreator: NotificationCreator) : PlaybackNotificator {
    companion object {
        private val REMOVE_NOTIFICATION: Boolean = true
        private val NOTIFICATION_ID: Int = 1
    }

    override fun checkIfShoudBeForeground(notificatorService: NotificatorService): Boolean {
        val shouldBeForeground = shouldBeForeground(notificatorService)
        logger.debug(PlaybackNotificatorImpl::class.java.simpleName, "checkIfShoudBeForeground = " + shouldBeForeground)
        if (shouldBeForeground) {
            startForeground(notificatorService)
        } else {
            stopForeground(notificatorService)
        }
        return shouldBeForeground
    }

    private fun stopForeground(notificatorService: NotificatorService) {
        notificatorService.stopForeground(REMOVE_NOTIFICATION);
    }

    private fun startForeground(notificatorService: NotificatorService) {
        notificatorService.startForeground(NOTIFICATION_ID, notificationCreator.createNotification(notificatorService));
    }

    private fun shouldBeForeground(notificatorService: NotificatorService) = !notificatorService.boundToFullScreen
}

