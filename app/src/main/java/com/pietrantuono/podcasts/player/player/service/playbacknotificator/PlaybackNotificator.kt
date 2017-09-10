package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.content.Context
import android.graphics.Bitmap
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener


interface PlaybackNotificator {
    fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat, bitmap: Bitmap?): Boolean
    fun updateNotification(context: Context, notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: PlaybackStateCompat, playWhenReady: Boolean, bitmap: Bitmap?)
    fun loadImage(episode: String?, listener: ImageLoadingListener)
}