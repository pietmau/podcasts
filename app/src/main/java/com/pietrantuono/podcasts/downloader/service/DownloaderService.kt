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
        const val TAG = "DownloaderService"
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
        debugLogger.debug(TAG,"onCreate")
        internalDownloader.addListener(this@DownloaderService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        debugLogger.debug(TAG,"onStartCommand")
        intent?.let { startDownload(intent) }
        return START_STICKY
    }

    private fun startDownload(intent: Intent) {
        debugLogger.debug(TAG,"startDownload")
        if (!shouldDownload()) {
            return
        }
        val url = intent.getStringExtra(TRACK)
        if (url != null) {
            getAndEnqueueSingleEpisode(url)
            return
        }
        getTracksUrls(intent)
    }

    private fun getTracksUrls(intent: Intent) {
        debugLogger.debug(TAG,"getTracksUrls")
        val list = intent.getStringArrayListExtra(TRACK_LIST)
        if (list == null) {
            return
        }
        for (url in list) {
            if (!thereIsEnoughSpace(0)) {
                notifySpaceUnavailable(url)
                break
            }
            getAndEnqueueSingleEpisode(url)
        }
    }

    private fun getAndEnqueueSingleEpisode(url: String) {
        debugLogger.debug(TAG,"getAndEnqueueSingleEpisode")
        internalDownloader.download(url)
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        debugLogger.debug(TAG,"onUpdate")
        internalDownloader.getRequestById(id)?.let {
            if (thereIsEnoughSpace(fileSize)) {
                downloadNotificator.notifyProgress(this@DownloaderService, it, progress)
            } else {
                stopDownloadAndNotifyUser(it)
            }
        }
        updateEpisodIfAppropriate(progress, id)
    }

    private fun updateEpisodIfAppropriate(progress: Int, id: Long) {
        debugLogger.debug(TAG,"updateEpisodIfAppropriate")
        if (progress >= DOWNLOAD_COMPLETED) {
            internalDownloader.getRequestById(id)?.let { onDownloadCompleted(it) }
        }
    }

    private fun onDownloadCompleted(requestInfo: RequestInfo) {
        debugLogger.debug(TAG,"onDownloadCompleted")
        stopForeground(false)
        downloadsManager.onDownloadCompleted(requestInfo)
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        debugLogger.debug(TAG,"stopDownloadAndNotifyUser")
        internalDownloader.stopDownload()
        notifySpaceUnavailable(requestInfo)
        stopSelf()
    }

    private fun thereIsEnoughSpace(fileSize: Long) = internalDownloader.thereIsEnoughSpace(fileSize)

    private fun shouldDownload() = networkAndPreferencesManager.shouldDownload

    private fun notifySpaceUnavailable(requestInfo: RequestInfo) {
        debugLogger.debug(TAG,"notifySpaceUnavailable")
        downloadNotificator.notifySpaceUnavailable(requestInfo)
    }

    private fun notifySpaceUnavailable(url: String) {
        debugLogger.debug(TAG,"notifySpaceUnavailable")
        downloadNotificator.notifySpaceUnavailable(url)
    }
}

