package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.app.TaskStackBuilder
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.main.view.MainActivity
import com.pietrantuono.podcasts.player.player.service.ResourceHelper
import com.pietrantuono.podcasts.utils.EPISODE_LINK


class SimpleNotificationBuilder(
        private val context: Context,
        sessionToken: MediaSessionCompat.Token?,
        description: MediaDescriptionCompat)
    : NotificationCompat.Builder(context) {

    private val playPauseButtonPosition = 0

    init {
        setSmallIcon(R.drawable.ic_notification)
        setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        setUsesChronometer(true)
        setContentTitle(description.title)
        setContentText(description.subtitle)
        setStyle(NotificationCompat.MediaStyle()
                .setShowActionsInCompactView(
                        *intArrayOf(playPauseButtonPosition))  // show only play/pause in compact view
                .setMediaSession(sessionToken))
        setColor(getNotificationColor())
        setContentIntent(createContentIntent(description))
    }

    private fun getNotificationColor() = ResourceHelper.getThemeColor(mContext, R.attr.colorPrimary, Color.DKGRAY)

    private fun createContentIntent(description: MediaDescriptionCompat?): PendingIntent? {
        if (description?.mediaUri != null) {
            val intent = Intent(mContext, FullscreenPlayActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.putExtra(EPISODE_LINK, description.mediaUri.toString())
            val pendingIntent = TaskStackBuilder.create(context)
                    .addParentStack(MainActivity::class.java)
                    .addNextIntentWithParentStack(intent)
                    .getPendingIntent(NotificationsConstants.REQUEST_CODE, PendingIntent.FLAG_CANCEL_CURRENT)
            return pendingIntent
        } else {
            return null
        }
    }


}