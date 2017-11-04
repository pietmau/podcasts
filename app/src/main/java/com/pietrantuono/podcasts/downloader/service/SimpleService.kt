package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder


abstract class SimpleService: Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}