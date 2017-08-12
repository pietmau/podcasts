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
import javax.inject.Inject

class DowloaderService : Service(), FetchListener {
    private val requests: MutableMap<Long, Request> = mutableMapOf()

    companion object {
        private val TAG: String = "DowloaderService"
        val TRACK: String = "track_id"
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
        (application as App).applicationComponent?.with(DownloadModule(this))?.
                inject(this)
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
        val url = intent.getStringExtra(TRACK)
        if (url != null) {
            getAndEnqueueSingleEpisode(url)
        } else {
            val trackList = intent.getStringArrayListExtra(TRACK_LIST)
            if (trackList != null) {
                for (trackId in trackList) {
                    getAndEnqueueSingleEpisode(trackId)
                }
            }
        }
    }

    private fun getAndEnqueueSingleEpisode(url: String) {
        repository.getEpisodeByUrl(url)?.let {
            it.enclosures?.let {
                if (!it.isEmpty()) {
                    requestGenerator.createRequest(it[0])?.let {
                        requests.put(downloader.enqueueRequest(it), it)
                    }

                }
            }
        }
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        debugLogger.debug(TAG, "" + progress)
        notification.notifyUser(requests[id], downloader.getById(id), id, status, progress, downloadedBytes, fileSize, error)
    }
}

