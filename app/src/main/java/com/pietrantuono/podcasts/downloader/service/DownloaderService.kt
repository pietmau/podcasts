package com.pietrantuono.podcasts.downloader.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : Service(), FetchListener {
    companion object {
        const val COMMAND_DOWNLOAD_EPISODE: String = "download_episode"
        const val COMMAND_DOWNLOAD_ALL_EPISODES: String = "download_all_episodes"
        const val COMMAND_DELETE_EPISODE: String = "delete_episode"
        const val COMMAND_DELETE_ALL_EPISODES: String = "delete_episode_all_episodes"

        const val EXTRA_COMMAND: String = "command"
        const val EXTRA_TRACK: String = "track_id"
        const val EXTRA_TRACK_LIST: String = "track_list"
        const val EXTRA_DOWNLOAD_REQUEST_ID: String = "download_request_id"

        const val DOWNLOAD_COMPLETED: Int = 100
        const val TAG = "DownloaderService"
    }

    @Inject lateinit var internalDownloader: Fetcher
    @Inject lateinit var downloadNotificator: DownloadNotificator
    @Inject lateinit var debugLogger: DebugLogger
    @Inject lateinit var networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with()?.inject(this)
        internalDownloader.addListener(this@DownloaderService)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.getStringExtra(EXTRA_COMMAND)?.let {
            when (it) {
                COMMAND_DOWNLOAD_ALL_EPISODES -> downloadAllEpisodes(intent)
                COMMAND_DOWNLOAD_EPISODE -> downloadEpisode(intent)
                COMMAND_DELETE_ALL_EPISODES -> deleteAllEpisodes(intent)
                COMMAND_DELETE_EPISODE -> deleteEpisode(intent)
            }
        }
        return START_STICKY
    }

    private fun deleteEpisode(intent: Intent) {
        TODO("not implemented")
    }

    private fun deleteAllEpisodes(intent: Intent) {
        TODO("not implemented")
    }

    private fun downloadEpisode(intent: Intent) {
        if (!shouldDownload()) {
            return
        }
        intent.getStringExtra(EXTRA_TRACK)?.let { getAndEnqueueSingleEpisode(it) }
    }

    private fun downloadAllEpisodes(intent: Intent) {
        if (!shouldDownload()) {
            return
        }
        intent.getStringArrayListExtra(EXTRA_TRACK_LIST)?.let { enqueueEpisodes(it) }
    }

    private fun enqueueEpisodes(list: List<String>) {
        for (url in list) {
            if (!thereIsEnoughSpace(0)) {
                notifySpaceUnavailable(url)
                break
            }
            getAndEnqueueSingleEpisode(url)
        }
    }

    private fun getAndEnqueueSingleEpisode(url: String) {
        internalDownloader.download(url)
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        internalDownloader.getRequestById(id)?.let {
            if (thereIsEnoughSpace(fileSize)) {
                downloadNotificator.notifyProgress(this@DownloaderService, it, progress)
            } else {
                stopDownloadAndNotifyUser(it)
            }
        }
        checkIfComplete(progress, id, downloadedBytes)
    }

    private fun checkIfComplete(progress: Int, id: Long, downloadedBytes: Long) {
        if (progress >= DOWNLOAD_COMPLETED) {
            onDownloadCompleted(id, downloadedBytes)
        }
    }

    private fun onDownloadCompleted(requestInfo: Long, downloadedBytes: Long) {
        stopForeground(false)
        internalDownloader.onDownloadCompleted(requestInfo, downloadedBytes)
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        internalDownloader.stopDownload()
        notifySpaceUnavailable(requestInfo)
        stopSelf()
    }

    private fun thereIsEnoughSpace(fileSize: Long) = networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)

    private fun shouldDownload() = networkDiskAndPreferenceManager.shouldDownload

    private fun notifySpaceUnavailable(requestInfo: RequestInfo) {
        downloadNotificator.notifySpaceUnavailable(requestInfo)
    }

    private fun notifySpaceUnavailable(url: String) {
        downloadNotificator.notifySpaceUnavailable(url)
    }
}

