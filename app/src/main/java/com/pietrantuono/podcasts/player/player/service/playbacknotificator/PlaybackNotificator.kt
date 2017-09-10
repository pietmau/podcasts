package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.content.Context
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat


interface PlaybackNotificator {
    fun checkIfShoudBeForeground(notificatorService: NotificatorService, media: MediaDescriptionCompat?, state: PlaybackStateCompat): Boolean
    fun updateNotification(context: Context, notificatorService: NotificatorService, media: MediaDescriptionCompat?, playbackState: PlaybackStateCompat, playWhenReady: Boolean)
}