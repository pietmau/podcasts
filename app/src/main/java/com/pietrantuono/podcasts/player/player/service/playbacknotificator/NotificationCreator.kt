package com.pietrantuono.podcasts.player.player.service.playbacknotificator


import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader

class NotificationCreator(private val context: Context, private val imageLooader: SimpleImageLoader, val intentManager: IntentsManager) {

    private fun getTitle(context: Context): String? = context.getText(R.string.notification_title)?.toString()

    fun createNotification(mediaDescriptionCompat: MediaDescriptionCompat?, state: PlaybackStateCompat, bitmap: Bitmap?): Notification {
        val builder = NotificationCompat.Builder(context)

        setActions(builder, state)

        builder.setStyle(NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(*intArrayOf(1))  // show only play/pause in compact view
                .setMediaSession(null))
                .setColor(getNotificationColor(context))
                .setSmallIcon(R.drawable.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setContentIntent(createContentIntent(mediaDescriptionCompat))
                .setContentTitle(getTitle(context, mediaDescriptionCompat))
                .setContentText(getSubtitle(context, mediaDescriptionCompat))

        setBitmap(bitmap, builder)

        setNotificationPlaybackState(builder, state)

        return builder.build()
    }

    fun updateNotification(media: MediaDescriptionCompat?, playbackState: PlaybackStateCompat,
                           playWhenReady: Boolean, bitmap: Bitmap?): Notification = createNotification(media, playbackState, bitmap)

    private fun setBitmap(bitmap: Bitmap?, builder: NotificationCompat.Builder) {
        if (bitmap != null) {
            builder.setLargeIcon(bitmap)
        } else {
            val art = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.ic_default_art)
            builder.setLargeIcon(art)
        }
    }

    private fun createContentIntent(mediaDescriptionCompat: MediaDescriptionCompat?) =
            intentManager.createContentIntent(context, mediaDescriptionCompat?.getMediaDescription())

    private fun setActions(builder: NotificationCompat.Builder, state: PlaybackStateCompat) {
        builder.addAction(R.drawable.ic_stop_white, context.getString(R.string.stop), intentManager.stopIntent)
        addPlayPauseAction(builder, context, state)
    }

    private fun getSubtitle(context: Context, mediaDescriptionCompat: MediaDescriptionCompat?): CharSequence? = getSubtitle(context)

    private fun getTitle(context: Context, mediaDescriptionCompat: MediaDescriptionCompat?): String? = getTitle(context)

    private fun fetchBitmapFromURLAsync(fetchArtUrl: String?, notificationBuilder: NotificationCompat.Builder) {}

    private fun getNotificationColor(context: Context): Int = context.resources.getColor(R.color.colorPrimary)

    private fun getSubtitle(context: Context): CharSequence? = context.getText(R.string.notification_subtitle)?.toString()

    private fun addPlayPauseAction(builder: NotificationCompat.Builder, context: Context, playbackState: PlaybackStateCompat) {
        if (playbackState.state == PlaybackStateCompat.STATE_PLAYING) {
            val label = context.getString(R.string.label_pause)
            val icon = R.drawable.uamp_ic_pause_white_24dp
            val intent = intentManager.pauseIntent
            builder.addAction(android.support.v4.app.NotificationCompat.Action(icon, label, intent))
        } else {
            val label = context.getString(R.string.label_play)
            val icon = R.drawable.uamp_ic_play_arrow_white_24dp
            val intent = intentManager.playIntent
            builder.addAction(android.support.v4.app.NotificationCompat.Action(icon, label, intent))
        }
    }

    private fun setNotificationPlaybackState(builder: NotificationCompat.Builder, playbackState: PlaybackStateCompat) {
        if (playbackState.state == PlaybackStateCompat.STATE_PLAYING && playbackState.getPosition() >= 0) {
            builder.setWhen(System.currentTimeMillis() - playbackState.getPosition())
                    .setShowWhen(true)
                    .setUsesChronometer(true)
        } else {
            builder.setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false)
        }
        builder.setOngoing(playbackState.getState() == PlaybackStateCompat.STATE_PLAYING)
    }
}

