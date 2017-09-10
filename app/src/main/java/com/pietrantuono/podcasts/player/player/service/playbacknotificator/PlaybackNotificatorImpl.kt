package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.support.v4.media.MediaDescriptionCompat
import com.pietrantuono.podcasts.application.DebugLogger

class PlaybackNotificatorImpl(private val logger: DebugLogger,
                              private val notificationCreator: NotificationCreator) : PlaybackNotificator {
    companion object {
        private val REMOVE_NOTIFICATION: Boolean = true
        private val NOTIFICATION_ID: Int = 1
    }

    override fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?): Boolean {
        val shouldBeForeground = shouldBeForeground(notificatorService)
        logger.debug(PlaybackNotificatorImpl::class.java.simpleName, "checkIfShoudBeForeground = " + shouldBeForeground)
        if (shouldBeForeground) {
            startForeground(notificatorService, media)
        } else {
            stopForeground(notificatorService)
        }
        return shouldBeForeground
    }

    private fun stopForeground(notificatorService: NotificatorService) {
        notificatorService.stopForeground(REMOVE_NOTIFICATION);
    }

    private fun startForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?) {
        val notification = notificationCreator.createNotification(media)
        notificatorService.startForeground(NOTIFICATION_ID, notification);
    }

    private fun shouldBeForeground(notificatorService: NotificatorService) = !notificatorService.boundToFullScreen

    override fun updateNotification(notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: Int, playWhenReady: Boolean) {
        if (!shouldBeForeground(notificatorService)) {
            return
        }
        val notification = notificationCreator.updateNotification(media, playbackState, playWhenReady)
        notificatorService.updateNotification(NOTIFICATION_ID, notification);
    }
}

