package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.content.res.TypedArrayUtils.getText
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity


class NotificationCreator {

    fun createNotification(notificatorService: NotificatorService): Notification {
        val context = getContext(notificatorService)
        createNotification(context)
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


    private fun createNotification(context: Context): Notification? {
        val notificationBuilder = NotificationCompat.Builder(context)
        var playPauseButtonPosition = 0

        // If skip to previous action is enabled
        val mPreviousIntent = null
        notificationBuilder.addAction(R.drawable.ic_skip_previous_white_24dp,
                context.getString(R.string.label_previous), mPreviousIntent)

        // If there is a "skip to previous" button, the play/pause button will
        // be the second one. We need to keep track of it, because the MediaStyle notification
        // requires to specify the index of the buttons (actions) that should be visible
        // when in compact view.
        playPauseButtonPosition = 1


        addPlayPauseAction(notificationBuilder)

        // If skip to next action is enabled

        notificationBuilder.addAction(R.drawable.ic_skip_next_white_24dp,
                mService.getString(R.string.label_next), mNextIntent)

        val description = mMetadata.getDescription()

        var fetchArtUrl: String? = null
        var art: Bitmap? = null
        if (description.getIconUri() != null) {
            // This sample assumes the iconUri will be a valid URL formatted String, but
            // it can actually be any valid Android Uri formatted String.
            // async fetch the album art icon
            val artUrl = description.getIconUri()!!.toString()
            art = AlbumArtCache.getInstance().getBigImage(artUrl)
            if (art == null) {
                fetchArtUrl = artUrl
                // use a placeholder art while the remote art is being downloaded
                art = BitmapFactory.decodeResource(mService.getResources(),
                        R.drawable.ic_default_art)
            }
        }

        notificationBuilder
                .setStyle(NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(
                                *intArrayOf(playPauseButtonPosition))  // show only play/pause in compact view
                        .setMediaSession(mSessionToken))
                .setColor(mNotificationColor)
                .setSmallIcon(R.drawable.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setContentIntent(createContentIntent(description))
                .setContentTitle(description.getTitle())
                .setContentText(description.getSubtitle())
                .setLargeIcon(art)

        if (mController != null && mController.getExtras() != null) {
            val castName = mController.getExtras().getString(MusicService.EXTRA_CONNECTED_CAST)
            if (castName != null) {
                val castInfo = mService.getResources()
                        .getString(R.string.casting_to_device, castName)
                notificationBuilder.setSubText(castInfo)
                notificationBuilder.addAction(R.drawable.ic_close_black_24dp,
                        mService.getString(R.string.stop_casting), mStopCastIntent)
            }
        }

        setNotificationPlaybackState(notificationBuilder)
        if (fetchArtUrl != null) {
            fetchBitmapFromURLAsync(fetchArtUrl, notificationBuilder)
        }

        return notificationBuilder.build()
    }

    private fun addPlayPauseAction(builder: NotificationCompat.Builder, context: Context) {
        val label: String
        val icon: Int
        val intent: PendingIntent
        if (getPlaybackState() == PlaybackStateCompat.STATE_PLAYING) {
            label = context.getString(R.string.label_pause)
            icon = R.drawable.uamp_ic_pause_white_24dp
            intent = getPauseIntent()
        } else {
            label = context.getString(R.string.label_play)
            icon = R.drawable.uamp_ic_play_arrow_white_24dp
            intent = getPlayIntent()
        }
        builder.addAction(NotificationCompat.Action(icon, label, intent))
    }

    private fun getPlayIntent(): PendingIntent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getPauseIntent(): PendingIntent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getPlaybackState(): Any {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}