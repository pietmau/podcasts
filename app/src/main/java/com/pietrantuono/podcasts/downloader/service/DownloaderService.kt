package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : SimpleService(), Fetcher.Callback {
    @Inject lateinit var internalDownloader: Fetcher
    @Inject lateinit var notificator: DownloadNotificator
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
        delete(intent.getLongExtra(EXTRA_DOWNLOAD_REQUEST_ID, -1))
    }

    private fun deleteAllEpisodes(intent: Intent) {
        (intent.getSerializableExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST) as? ArrayList<Long>)?.forEach {
            delete(it)
        }
    }

    private fun delete(id: Long) {
        internalDownloader.deleteEpisode(id)
        notificator.broadcastDeleteEpisode(id)
    }

    private fun downloadEpisode(intent: Intent) {
        if (!shouldDownload()) {
            intent.getStringExtra(EXTRA_TRACK)?.let {
                getAndEnqueueSingleEpisode(it) }
        }
    }

    private fun downloadAllEpisodes(intent: Intent) {
        if (shouldDownload()) {
            intent.getStringArrayListExtra(EXTRA_TRACK_LIST)?.let { enqueueEpisodes(it) }
        }
    }

    private fun enqueueEpisodes(list: List<String>) {
        for (uri in list) {
            if (!thereIsEnoughSpace(0)) {
                notificator.notifySpaceUnavailable(uri)
                break
            }
            getAndEnqueueSingleEpisode(uri)
        }
    }

    private fun getAndEnqueueSingleEpisode(uri: String) {
        internalDownloader.download(uri)
    }

    override fun onUpdate(info: RequestInfo, progress: Int, fileSize: Long) {
        notificator.broadcastUpdate(info, progress, fileSize)
        if (thereIsEnoughSpace(fileSize)) {
            notificator.notifyProgress(this@DownloaderService, info, progress)
            return
        }
        stopDownloadAndNotifyUser(info)
    }

    override fun onDownloadCompleted(requestInfo: RequestInfo) {
        notificator.broadcastOnDownloadCompleted(requestInfo)
        stopForeground(false)
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        notificator.notifySpaceUnavailable(requestInfo)
        shutDownDownloader()
        stopSelf()
    }

    internal fun thereIsEnoughSpace(fileSize: Long) = networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)

    internal fun shouldDownload() = networkDiskAndPreferenceManager.shouldDownload

    override fun onDestroy() {
        shutDownDownloader()
    }

    private fun shutDownDownloader() {
        internalDownloader.shutDown()
    }
}

