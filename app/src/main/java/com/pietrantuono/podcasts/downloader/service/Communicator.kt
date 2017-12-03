package com.pietrantuono.podcasts.downloader.service

import android.app.Notification
import android.content.Intent

interface Communicator {
    fun notify(id: Int, noSpaceNotification: Notification?)
    fun sendBoroadcast(intent: Intent)
    fun removeNotification(id: Int)

}