package com.pietrantuono.podcasts.downloader

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.repository.EpisodesRepository


class DowloaderService : Service() {
    companion object {
        val TRACK_ID: String = "track_id"
    }

    private lateinit var downloader: Dowloader
    private lateinit var repository: EpisodesRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        startDownload(intent)
        return START_STICKY
    }

    private fun startDownload(intent: Intent?) {
        if (intent == null || intent.getStringExtra(TRACK_ID) == null) {
            return
        }
        val trackId = intent.getStringExtra(TRACK_ID)

    }

}