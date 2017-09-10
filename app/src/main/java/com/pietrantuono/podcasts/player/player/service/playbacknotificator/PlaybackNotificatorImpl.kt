package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader

class PlaybackNotificatorImpl(private val logger: DebugLogger,
                              private val notificationCreator: NotificationCreator, private val imageLoader: SimpleImageLoader) : PlaybackNotificator {
    override fun loadImage(imageUrl: String?, listener: ImageLoadingListener) {
        imageUrl?.let {
            imageLoader.loadImage(it, listener)
        }
    }

    companion object {
        val REMOVE_NOTIFICATION: Boolean = true
        private val NOTIFICATION_ID: Int = 1
    }

    override fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat, bitmap: Bitmap?): Boolean {
        val shouldBeForeground = shouldBeForeground(notificatorService, media)
        logger.debug(PlaybackNotificatorImpl::class.java.simpleName, "checkIfShoudBeForeground = " + shouldBeForeground)
        if (shouldBeForeground) {
            startForeground(notificatorService, media, state, bitmap)
        } else {
            stopForeground(notificatorService)
        }
        return shouldBeForeground
    }

    private fun stopForeground(notificatorService: NotificatorService) {
        notificatorService.stopForeground(REMOVE_NOTIFICATION);
    }

    private fun startForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat, bitmap: Bitmap?) {
        val notification = notificationCreator.createNotification(media, state, bitmap)
        notificatorService.startForeground(NOTIFICATION_ID, notification);
    }

    private fun shouldBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?): Boolean {
        val isBound = notificatorService.boundToFullScreen
        val hasMedia = media != null
        return !isBound && hasMedia
    }

    override fun updateNotification(context: Context, notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: PlaybackStateCompat, playWhenReady: Boolean, bitmap: Bitmap?) {
        if (!shouldBeForeground(notificatorService, media)) {
            return
        }
        val notification = notificationCreator.updateNotification(media, playbackState, playWhenReady, bitmap)
        (context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(NOTIFICATION_ID, notification);
    }
}
