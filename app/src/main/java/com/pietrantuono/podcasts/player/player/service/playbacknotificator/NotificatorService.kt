package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification

interface NotificatorService {
    var boundToFullScreen: Boolean
    fun checkIfShoudBeForeground()
    fun startForeground(notificationId: Int, notification: Notification)
    fun stopForeground(removeNotifcationo: Boolean)
}