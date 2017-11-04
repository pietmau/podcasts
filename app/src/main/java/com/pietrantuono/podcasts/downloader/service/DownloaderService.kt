package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : SimpleService(), Fetcher.Callback {

    companion object {
        const val COMMAND_DOWNLOAD_EPISODE: String = "download_episode"
        const val COMMAND_DOWNLOAD_ALL_EPISODES: String = "download_all_episodes"
        const val COMMAND_DELETE_EPISODE: String = "delete_episode"
        const val COMMAND_DELETE_ALL_EPISODES: String = "delete_episode_all_episodes"

        const val EXTRA_COMMAND: String = "command"
        const val EXTRA_TRACK: String = "track_id"
        const val EXTRA_TRACK_LIST: String = "track_list"
        const val EXTRA_DOWNLOAD_REQUEST_ID: String = "download_request_id"
        const val EXTRA_DOWNLOAD_REQUEST_ID_LIST: String = "download_request_id_list"

        const val DOWNLOAD_COMPLETED: Int = 100
        const val TAG = "DownloaderService"
    }

    @Inject lateinit var internalDownloader: Fetcher
    @Inject lateinit var notificator: DownloadNotificator
    @Inject lateinit var debugLogger: DebugLogger
    @Inject lateinit var networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with()?.inject(this)
        internalDownloader.addCallback(this@DownloaderService)
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
        internalDownloader.deleteEpisode(intent.getLongExtra(EXTRA_DOWNLOAD_REQUEST_ID, -1))
    }

    private fun deleteAllEpisodes(intent: Intent) {
        (intent.getSerializableExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST) as? ArrayList<Long>)?.forEach {
            internalDownloader.deleteEpisode(it)
        }
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

    override fun onUpdate(info: RequestInfo, progress: Int, fileSize: Long) {
        debugLogger.debug(TAG, "onUpdate " + info.id)
        if (thereIsEnoughSpace(fileSize)) {
            notificator.notifyProgress(this@DownloaderService, info, progress)
        } else {
            stopDownloadAndNotifyUser(info)
        }
    }

    override fun onDownloadCompleted() {
        stopForeground(false)
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        notificator.notifySpaceUnavailable(requestInfo)
        shutDownDownloader()
        stopSelf()
    }

    private fun thereIsEnoughSpace(fileSize: Long) = networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)

    private fun shouldDownload() = networkDiskAndPreferenceManager.shouldDownload

    private fun notifySpaceUnavailable(url: String) {
        notificator.notifySpaceUnavailable(url)
    }

    override fun onDestroy() {
        shutDownDownloader()
    }

    private fun shutDownDownloader() {
        internalDownloader.shutDown()
    }

}

