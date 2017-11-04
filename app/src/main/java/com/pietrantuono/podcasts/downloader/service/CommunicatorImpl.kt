package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.app.NotificationManager
import android.content.Intent
import android.support.v4.content.LocalBroadcastManager

class CommunicatorImpl(
        private val notificatiopnManager: NotificationManager,
        private val localBroadcastManager: LocalBroadcastManager) : Communicator {

    override fun sendBoroadcast(intent: Intent) {
        localBroadcastManager.sendBroadcast(intent)
    }

    override fun notify(id: Int, notification: Notification?) {
        notificatiopnManager.notify(id, notification)
    }
}