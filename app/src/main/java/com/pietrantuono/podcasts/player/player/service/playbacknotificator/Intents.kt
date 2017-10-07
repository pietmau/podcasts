package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import javax.inject.Inject


class Intents @Inject constructor() {

    fun getPauseIntent(service: Service) = PendingIntent.getBroadcast(service, NotificationsConstants.REQUEST_CODE,
            Intent(NotificationsConstants.ACTION_PAUSE).setPackage(service.packageName), PendingIntent.FLAG_CANCEL_CURRENT)

    fun getPlayIntent(service: Service) = PendingIntent.getBroadcast(service, NotificationsConstants.REQUEST_CODE,
            Intent(NotificationsConstants.ACTION_PLAY).setPackage(service.packageName), PendingIntent.FLAG_CANCEL_CURRENT)

    fun getStopcastIntent(service: Service) = PendingIntent.getBroadcast(service, NotificationsConstants.REQUEST_CODE,
            Intent(NotificationsConstants.ACTION_STOP_CASTING).setPackage(service.packageName), PendingIntent.FLAG_CANCEL_CURRENT)
}