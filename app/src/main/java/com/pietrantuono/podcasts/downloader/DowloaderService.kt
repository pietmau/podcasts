package com.pietrantuono.podcasts.downloader

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import javax.inject.Inject

class DowloaderService : Service(), FetchListener {
    private val requests: MutableMap<Long, Request> = mutableMapOf()

    companion object {
        val TRACK_ID: String = "track_id"
    }

    @Inject lateinit var downloader: Downloader
    @Inject lateinit var repository: EpisodesRepository
    @Inject lateinit var notification: Notificator
    @Inject lateinit var requestGenerator: RequestGenerator

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        downloader.addListener(this@DowloaderService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startDownload(intent)
        return START_STICKY
    }

    private fun startDownload(intent: Intent?) {
        if (intent == null || intent.getStringExtra(TRACK_ID) == null) {
            return
        }
        val episode = repository.getEpisoceById(intent.getStringExtra(TRACK_ID))
        if (episode == null) {
            return
        }
        val request = requestGenerator.createRequest(episode)
        val id = downloader.enqueueRequest(request)
        requests.put(id, request)
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        notification.notifyUser(requests[id], downloader.getById(id), id, status, progress, downloadedBytes, fileSize, error)
    }
}

