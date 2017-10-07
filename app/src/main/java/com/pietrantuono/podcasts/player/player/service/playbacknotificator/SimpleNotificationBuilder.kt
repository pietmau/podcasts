package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.player.player.service.ResourceHelper


class SimpleNotificationBuilder(
        context: Context,
        private val sessionToken: MediaSessionCompat.Token?,
        private val description: MediaDescriptionCompat)
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

    private fun createContentIntent(description: MediaDescriptionCompat?): PendingIntent {
        val openUI = Intent(mContext, FullscreenPlayActivity::class.java)
        openUI.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        //openUI.putExtra(MusicPlayerActivity.EXTRA_START_FULLSCREEN, true)
        if (description != null) {
            openUI.putExtra(FullscreenPlayActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, description)
        }
        return PendingIntent.getActivity(mContext, NotificationsConstants.REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT)
    }


}