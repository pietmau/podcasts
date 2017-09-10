package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.support.v4.media.MediaDescriptionCompat


interface PlaybackNotificator {
    fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?): Boolean
    fun updateNotification(notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: Int, playWhenReady: Boolean)
}