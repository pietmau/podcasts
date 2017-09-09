package com.pietrantuono.podcasts.player.player.service.playbacknotificator


import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity

class NotificationCreator(private val context: Context, private val albumArtCache: AlbumArtCache, val intentManager: IntentManager) {

    fun createNotification(notificatorService: NotificatorService): Notification {
        val context = getContext(notificatorService)

        return Notification.Builder(context)
                .setContentTitle(getTitle(context))
                .setContentText(getText(context))
                .setSmallIcon(getIconRes())
                .setContentIntent(getPendingIntent(context))
                .build()
    }

    private fun getPendingIntent(context: Context) = PendingIntent.getActivity(context, 0, Intent(context, FullscreenPlayActivity::class.java), 0)

    private fun getIconRes(): Int = R.mipmap.ic_launcher

    private fun getText(context: Context): String? = context.getText(R.string.notification_message)?.toString()

    private fun getTitle(context: Context): String? = context.getText(R.string.notification_title)?.toString()

    private fun getContext(notificatorService: NotificatorService) = notificatorService as Context

    fun createNotification(mediaDescriptionCompat: MediaDescriptionCompat?): Notification {
        val notificationBuilder = NotificationCompat.Builder(context)

        val previousIntent = intentManager.getPreviousIntent(context)

        notificationBuilder.addAction(R.drawable.ic_skip_previous_white_24dp,
                context.getString(R.string.label_previous), previousIntent)

        addPlayPauseAction(notificationBuilder, context)

        val nextIntent = intentManager.getNextIntent(context)
        notificationBuilder.addAction(R.drawable.ic_skip_next_white_24dp,
                context.getString(R.string.label_next), nextIntent)

        val description = mediaDescriptionCompat?.getMediaDescription()
        var fetchArtUrl: String? = null
        var art: Bitmap? = null
        val iconUri = mediaDescriptionCompat?.getIconUri()
        if (iconUri != null) {
            val artUrl = iconUri.toString()
            art = albumArtCache.getBigImage(artUrl)
            if (art == null) {
                fetchArtUrl = artUrl
                art = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_art)
            }
        }
        notificationBuilder
                .setStyle(NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(*intArrayOf(1))  // show only play/pause in compact view
                        .setMediaSession(null))
                .setColor(getNotificationColor(context))
                .setSmallIcon(R.drawable.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setContentIntent(intentManager.createContentIntent(context, description))
                .setContentTitle(getTitle(context, mediaDescriptionCompat))
                .setContentText(getSubtitle(context, mediaDescriptionCompat))
                .setLargeIcon(art)
        setNotificationPlaybackState(notificationBuilder)
        if (fetchArtUrl != null) {
            fetchBitmapFromURLAsync(fetchArtUrl, notificationBuilder)
        }
        return notificationBuilder.build()
    }

    private fun getSubtitle(context: Context, mediaDescriptionCompat: MediaDescriptionCompat?): CharSequence? = getSubtitle(context)

    private fun getTitle(context: Context, mediaDescriptionCompat: MediaDescriptionCompat?): String? = getTitle(context)

    private fun fetchBitmapFromURLAsync(fetchArtUrl: String, notificationBuilder: NotificationCompat.Builder) {
        //TODO update the icon
    }

    private fun getNotificationColor(context: Context): Int = context.resources.getColor(R.color.colorPrimary)

    private fun getSubtitle(context: Context): CharSequence? = context.getText(R.string.notification_subtitle)?.toString()

    private fun addPlayPauseAction(builder: NotificationCompat.Builder, context: Context) {
        val label: String
        val icon: Int
        val intent: PendingIntent
        if (getPlaybackState().state == PlaybackStateCompat.STATE_PLAYING) {
            label = context.getString(R.string.label_pause)
            icon = R.drawable.uamp_ic_pause_white_24dp
            intent = intentManager.getPauseIntent(context)
        } else {
            label = context.getString(R.string.label_play)
            icon = R.drawable.uamp_ic_play_arrow_white_24dp
            intent = intentManager.getPlayIntent(context)
        }
        builder.addAction(android.support.v4.app.NotificationCompat.Action(icon, label, intent))
    }

    private fun getPlaybackState(): PlaybackStateCompat {
        val builder = PlaybackStateCompat.Builder()
        builder.setState(PlaybackStateCompat.STATE_BUFFERING, 100, 10f)
        return builder.build()
    }

    private fun setNotificationPlaybackState(builder: NotificationCompat.Builder) {
        val playbackState = getPlaybackState()
        if (playbackState.state == PlaybackStateCompat.STATE_PLAYING && playbackState.getPosition() >= 0) {
            builder
                    .setWhen(System.currentTimeMillis() - playbackState.getPosition())
                    .setShowWhen(true)
                    .setUsesChronometer(true)
        } else {
            builder
                    .setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false)
        }
        builder.setOngoing(playbackState.getState() == PlaybackStateCompat.STATE_PLAYING)
    }

}

