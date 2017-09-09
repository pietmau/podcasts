package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.Notification
import android.support.v4.media.MediaDescriptionCompat

interface NotificatorService {
    var boundToFullScreen: Boolean
    fun checkIfShoudBeForeground()
    fun startForeground(notificatioN_ID: Int, notification: Notification)
    fun stopForeground(removeNotifcationo: Boolean)
}