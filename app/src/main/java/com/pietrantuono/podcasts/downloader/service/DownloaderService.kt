package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : Service(), FetchListener {
    companion object {
        const val TRACK: String = "track_id"
        const val TRACK_LIST: String = "track_list"
        const val DOWNLOAD_COMPLETED: Int = 100
    }

    @Inject lateinit var internalDownloader: Fetcher
    @Inject lateinit var downloadNotificator: DownloadNotificator
    @Inject lateinit var debugLogger: DebugLogger
    @Inject lateinit var networkAndPreferencesManager: NetworkDetector
    @Inject lateinit var downloadsManager: CompletedDownloadsManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        internalDownloader.addListener(this@DownloaderService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let { startDownload(intent) }
        return START_STICKY
    }

    private fun startDownload(intent: Intent) {
        intent.getStringExtra(TRACK)?.also { getAndEnqueueSingleEpisode(it) } ?: getTracksUrls(intent)
    }

    private fun getTracksUrls(intent: Intent) {
        intent.getStringArrayListExtra(TRACK_LIST)?.let { list -> list.forEach { url -> getAndEnqueueSingleEpisode(url) } }
    }

    private fun getAndEnqueueSingleEpisode(url: String) {
        if (networkAndPreferencesManager.shouldDownload) {
            internalDownloader.download(url)
        }
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        internalDownloader.getRequestById(id)?.let {
            if (internalDownloader.thereIsEnoughSpace(fileSize)) {
                downloadNotificator.notifyProgress(this@DownloaderService, it, progress)
            } else {
                stopDownloadAndNotifyUser(it)
            }
        }
        updateEpisodIfAppropriate(progress, id)
    }

    private fun updateEpisodIfAppropriate(progress: Int, id: Long) {
        if (progress >= DOWNLOAD_COMPLETED) {
            internalDownloader.getRequestById(id)?.let { onDownloadCompleted(it) }
        }
    }

    private fun onDownloadCompleted(requestInfo: RequestInfo) {
        stopForeground(false)
        downloadsManager.onDownloadCompleted(requestInfo)
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        internalDownloader.stopDownload()
        downloadNotificator.notifySpaceUnavailable(requestInfo)
        stopSelf()
    }
}

