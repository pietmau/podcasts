package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification
import android.app.Service
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R

class NotificationCreatorImpl(
        private val service: Service,
        private val notificationManagerCompat: NotificationManagerCompat,
        private val iconCache: IconCache,
        private val intents: Intents)
    : NotificationCreator {

    override fun createNotification(metadata: MediaMetadataCompat?, playbackState: PlaybackStateCompat?,
                                    sessionToken: MediaSessionCompat.Token?, started: Boolean): Notification? {
        if (metadata == null || playbackState == null) {
            return null
        }
        val description = metadata!!.description
        val notificationBuilder = SimpleNotificationBuilder(service, sessionToken, description)
        addPlayPauseAction(notificationBuilder, playbackState)
        setNotificationPlaybackState(notificationBuilder, playbackState, started)
        iconCache.setOrGetIcon(notificationManagerCompat, description, notificationBuilder)
        return notificationBuilder.build()
    }

    private fun addPlayPauseAction(builder: NotificationCompat.Builder, playbackState: PlaybackStateCompat?) {
        var label = service.getString(R.string.label_play)
        var icon = R.drawable.uamp_ic_play_arrow_white_24dp
        var intent = intents.getPlayIntent(service)
        if (playbackState!!.state == PlaybackStateCompat.STATE_PLAYING) {
            label = service.getString(R.string.label_pause)
            icon = R.drawable.uamp_ic_pause_white_24dp
            intent = intents.getPauseIntent(service)
        }
        builder.addAction(android.support.v4.app.NotificationCompat.Action(icon, label, intent))
    }

    private fun setNotificationPlaybackState(builder: NotificationCompat.Builder,
                                             playbackState: PlaybackStateCompat?, started: Boolean) {
        if (playbackState == null || !started) {
            service.stopForeground(true)
            return
        }
        builder.setWhen(0).setShowWhen(false).setUsesChronometer(false)
        if (playbackState!!.state == PlaybackStateCompat.STATE_PLAYING && playbackState!!.position >= 0) {
            builder.setWhen(System.currentTimeMillis() - playbackState!!.position)
                    .setShowWhen(true).setUsesChronometer(true)
        }
        builder.setOngoing(playbackState!!.state == PlaybackStateCompat.STATE_PLAYING)
    }
}