package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification
import com.pietrantuono.podcasts.player.player.service.NotificatorService
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v4.content.res.TypedArrayUtils.getText
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity


class NotificationCreator {

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

}