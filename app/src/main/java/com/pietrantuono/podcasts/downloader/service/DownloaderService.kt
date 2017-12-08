package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.pietrantuono.podcasts.player.Enqueuer
import com.tonyodev.fetch.request.RequestInfo
import javax.inject.Inject

class DownloaderService() : SimpleService(), Fetcher.Callback {
    @Inject lateinit var dowloaderDelter: DowloaderDeleter
    @Inject lateinit var notificator: DownloadNotificator
    @Inject lateinit var enqueuer: Enqueuer

    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with()?.inject(this)
        dowloaderDelter.addCallback(this@DownloaderService)
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
        dowloaderDelter.deleteEpisode(intent.getLongExtra(EXTRA_DOWNLOAD_REQUEST_ID, -1))
    }

    private fun deleteAllEpisodes(intent: Intent) {
        dowloaderDelter.deleteAllEpisodes(intent)
    }

    private fun downloadEpisode(intent: Intent) {
        val requestInfo = dowloaderDelter.downloadEpisode(intent)?.second
        enqueuer.storeRequestIfAppropriate(requestInfo, intent.getBooleanExtra(PLAY_WHEN_READY, false))
    }

    private fun downloadAllEpisodes(intent: Intent) {
        dowloaderDelter.downloadAllEpisodes(intent)
    }

    override fun onUpdate(info: RequestInfo, staus: Int, progress: Int, fileSize: Long) {
        notificator.broadcastUpdate(info, progress, fileSize)
        notificator.notifyStatus(this@DownloaderService, info, staus, progress)
        if (!dowloaderDelter.thereIsEnoughDiskSpace(fileSize)) {
            stopDownloadAndNotifyUser(info)
        }
    }

    override fun onDownloadCompleted(requestInfo: RequestInfo) {
        notificator.broadcastOnDownloadCompleted(requestInfo)
        enqueuer.addToQueueIfAppropriate(requestInfo)
        checkIfDone()
    }

    private fun checkIfDone() {
        if (dowloaderDelter.allDone) {
            stopForeground(true)
            stopSelf()
        }
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        dowloaderDelter.shutDown()
        stopForeground(true)
        notificator.notifySpaceUnavailable(requestInfo)
        stopSelf()
    }

    override fun onRemoved(requestInfo: RequestInfo, id: Long) {
        checkIfDone()
    }

    override fun onDestroy() {
        dowloaderDelter.shutDown()
    }

}

