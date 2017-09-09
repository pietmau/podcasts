package com.pietrantuono.podcasts.player.player.service

import android.app.Notification

interface NotificatorService {
    var boundToFullScreen: Boolean
    fun checkIfShoudBeForeground()
    fun startForeground(notificatioN_ID: Int, notification: Notification)
    fun stopForeground(removeNotifcationo: Boolean)
}