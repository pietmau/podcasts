package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.pietrantuono.podcasts.downloader.downloader.RequestManager
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : Service(), FetchListener {
    companion object {
        const private val TAG: String = "DownloaderService"
        const val TRACK: String = "track_id"
        const val TRACK_LIST: String = "track_list"
        const val DOWNLOAD_COMPLETED: Int = 100
    }

    @Inject lateinit var internalDownloader: Fetcher
    @Inject lateinit var downloadNotificator: DownloadNotificator
    @Inject lateinit var requestManager: RequestManager
    @Inject lateinit var debugLogger: DebugLogger
    @Inject lateinit var repository: EpisodesRepository
    @Inject lateinit var networkDetector: NetworkDetector

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
        val url = intent.getStringExtra(TRACK);
        if (url != null) {
            getAndEnqueueSingleEpisode(url)
        } else {
            intent.getStringArrayListExtra(TRACK_LIST).let {
                for (url in it) {
                    getAndEnqueueSingleEpisode(url)
                }
            }
        }
    }

    private fun getAndEnqueueSingleEpisode(url: String) {
        if (networkDetector.shouldDownload)
            if (!internalDownloader.alreadyDownloaded(url) && !episodeIsDownloaded(url)) {
                requestManager.createRequest(url)?.let { request ->
                    requestManager.cacheRequest(internalDownloader.enqueueRequest(request))
                }
            }
    }

    fun episodeIsDownloaded(url: String): Boolean {
        val episode = repository.getEpisodeByUrlSync(url)
        return episode != null && episode.downloaded
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        debugLogger.debug(TAG, "" + progress)
        val request = requestManager.getRequestById(id)
        if (internalDownloader.thereIsEnoughSpace(fileSize)) {
            downloadNotificator.notifyProgress(request, progress)
        } else {
            interruptDownloadAndNotifyUser(request, progress)
        }
        updateEpisodIfAppropriate(progress, id)
    }

    private fun updateEpisodIfAppropriate(progress: Int, id: Long) {
        if (progress >= DOWNLOAD_COMPLETED) {
            onDownloadCompleted(requestManager.getRequestById(id))
        }
    }

    private fun onDownloadCompleted(episode: RequestInfo?) {
        //repository.onDownloadCompleted()
        TODO()
    }

    private fun interruptDownloadAndNotifyUser(id: RequestInfo?, progress: Int) {}
}

