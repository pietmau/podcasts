package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

interface NotificationCreator {
    fun createNotification(metadata: MediaMetadataCompat?, playbackState: PlaybackStateCompat?,
                           sessionToken: MediaSessionCompat.Token?, started: Boolean)
            : Notification?
}