package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.NotificationManager
import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.application.DebugLogger

class PlaybackNotificatorImpl(private val logger: DebugLogger,
                              private val notificationCreator: NotificationCreator) : PlaybackNotificator {
    companion object {
        val REMOVE_NOTIFICATION: Boolean = true
        private val NOTIFICATION_ID: Int = 1
    }

    override fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat): Boolean {
        val shouldBeForeground = shouldBeForeground(notificatorService)
        logger.debug(PlaybackNotificatorImpl::class.java.simpleName, "checkIfShoudBeForeground = " + shouldBeForeground)
        if (shouldBeForeground) {
            startForeground(notificatorService, media, state)
        } else {
            stopForeground(notificatorService)
        }
        return shouldBeForeground
    }

    private fun stopForeground(notificatorService: NotificatorService) {
        notificatorService.stopForeground(REMOVE_NOTIFICATION);
    }

    private fun startForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat) {
        val notification = notificationCreator.createNotification(media, state)
        notificatorService.startForeground(NOTIFICATION_ID, notification);
    }

    private fun shouldBeForeground(notificatorService: NotificatorService) = !notificatorService.boundToFullScreen

    override fun updateNotification(context: Context, notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: PlaybackStateCompat, playWhenReady: Boolean) {
        if (!shouldBeForeground(notificatorService)) {
            return
        }
        val notification = notificationCreator.updateNotification(media, playbackState, playWhenReady, )
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(NOTIFICATION_ID, notification);
    }
}

