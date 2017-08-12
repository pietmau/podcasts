package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloader.downloader.RequestGenerator
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import java.util.*
import javax.inject.Inject

class DowloaderService : Service(), FetchListener {
    private val requests: MutableMap<Long, Request> = mutableMapOf()

    companion object {
        val TRACK_ID: String = "track_id"
        val TRACK_LIST: String = "track_list"
    }

    @Inject lateinit var downloader: Downloader
    @Inject lateinit var repository: EpisodesRepository
    @Inject lateinit var notification: Notificator
    @Inject lateinit var requestGenerator: RequestGenerator
    @Inject lateinit var debugLogger: DebugLogger

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
        if (intent == null) {
            return
        }
        val trackId = intent.getStringExtra(TRACK_ID)
        if (trackId != null) {
            getAndEnqueueSingleEpisode(trackId)
        } else {
            val trackList = intent.getStringArrayListExtra(TRACK_LIST)
            if (trackList != null) {
                getAndEnqueueList(trackList)
            }
        }
    }

    private fun getAndEnqueueList(trackList: ArrayList<String>) {
        for (trackId in trackList) {
            getAndEnqueueSingleEpisode(trackId)
        }
    }

    private fun getAndEnqueueSingleEpisode(trackId: String) {
        val episode = repository.getEpisoceById(trackId)
        if (episode == null) {
            return
        }
        val request = requestGenerator.createRequest(episode)
        requests.put(downloader.enqueueRequest(request), request)
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        debugLogger.debug(this@DowloaderService::class.simpleName, "" + progress)
        notification.notifyUser(requests[id], downloader.getById(id), id, status, progress, downloadedBytes, fileSize, error)
    }
}

