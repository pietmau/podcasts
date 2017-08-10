package com.pietrantuono.podcasts.downloader

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App


class DowloaderService : Service() {
    private lateinit var downlodar: Dowloader

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        return START_REDELIVER_INTENT
    }


}