package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloader.downloader.RequestGenerator
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import javax.inject.Inject

class DownloaderService() : Service(), FetchListener {
    private val requests: MutableMap<Long, Pair<Request, Episode>> = mutableMapOf()

    companion object {
        const private val TAG: String = "DownloaderService"
        const val TRACK: String = "track_id"
        const val TRACK_LIST: String = "track_list"
        const val DOWNLOAD_COMPLETED: Int = 100
    }

    @Inject lateinit var downloader: Downloader
    @Inject lateinit var notificator: Notificator
    @Inject lateinit var requestGenerator: RequestGenerator
    @Inject lateinit var debugLogger: DebugLogger
    @Inject lateinit var repository: EpisodesRepository

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with(DownloadModule(this))?.
                inject(this)
        downloader.addListener(this@DownloaderService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { startDownload(intent) }
        return START_STICKY
    }

    private fun startDownload(intent: Intent) {
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
        if (!alreadyDownLoaded(url)) {
            requestGenerator.createRequest(url)?.let {
                requests.put(downloader.enqueueRequest(it.first), it)
            }
        }
    }

    private fun alreadyDownLoaded(url: String) = downloader.alreadyDownloaded(url)

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        debugLogger.debug(TAG, "" + progress)
        if (downloader.thereIsEnoughSpace(fileSize)) {
            notificator.notifyProgress(requests[id]?.second, id, progress)
        } else {
            interruptDownloadAndNotifyUser(id, progress)
        }
        updateEpisodIfAppropriate(progress, id)
    }

    private fun updateEpisodIfAppropriate(progress: Int, id: Long) {
        if (progress >= DOWNLOAD_COMPLETED) {
            onDownloadCompleted(requests[id]?.second)
        }
    }

    private fun onDownloadCompleted(episode: Episode?) {
        repository.onDownloadCompleted(episode)
    }

    private fun interruptDownloadAndNotifyUser(id: Long, progress: Int) {

    }
}

