package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.di.DownloadModule
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.pietrantuono.podcasts.player.Enqueuer
import com.tonyodev.fetch.request.RequestInfo
import hugo.weaving.DebugLog
import java.util.concurrent.Executors
import javax.inject.Inject

class DownloaderService() : SimpleService(), Fetcher.Callback {
    @Inject lateinit var dowloaderDelter: DowloaderDeleter
    @Inject lateinit var notificator: DownloadNotificator
    @Inject lateinit var enqueuer: Enqueuer
    @Inject lateinit var debugLogger: DebugLogger
    private val executor = Executors.newSingleThreadExecutor()

    val TAG = "DownloaderService"

    @DebugLog
    override fun onCreate() {
        super.onCreate()
        (application as App).applicationComponent?.with(DownloadModule(this))?.inject(this)
        dowloaderDelter.addCallback(this@DownloaderService)
    }

    @DebugLog
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "onStartCommand START")
        intent?.getStringExtra(EXTRA_COMMAND)?.let {
            when (it) {
                COMMAND_DOWNLOAD_ALL_EPISODES -> downloadAllEpisodes(intent)
                COMMAND_DOWNLOAD_EPISODE -> downloadEpisode(intent)
                COMMAND_DELETE_ALL_EPISODES -> deleteAllEpisodes(intent)
                COMMAND_DELETE_EPISODE -> deleteEpisode(intent)
            }
        }
        debugLogger.error(TAG, "onStartCommand END  " + (System.currentTimeMillis() - time))
        return START_STICKY
    }

    private fun deleteEpisode(intent: Intent) {
        dowloaderDelter.deleteEpisode(intent.getLongExtra(EXTRA_DOWNLOAD_REQUEST_ID, -1))
    }

    @DebugLog
    private fun deleteAllEpisodes(intent: Intent) {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "deleteAllEpisodes START")
        dowloaderDelter.deleteAllEpisodes(intent)
        debugLogger.error(TAG, "deleteAllEpisodes END  " + (System.currentTimeMillis() - time))
    }

    private fun downloadEpisode(intent: Intent) {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "downloadEpisode START")
        val requestInfo = dowloaderDelter.downloadEpisode(intent)?.second
        enqueuer.storeRequestIfAppropriate(requestInfo, intent.getBooleanExtra(PLAY_WHEN_READY, false))
        debugLogger.error(TAG, "downloadEpisode END  " + (System.currentTimeMillis() - time))
    }

    @DebugLog
    private fun downloadAllEpisodes(intent: Intent) {
        executor.execute { dowloaderDelter.downloadAllEpisodes(intent) }
    }

    override fun onUpdate(info: RequestInfo, staus: Int, progress: Int, fileSize: Long) {
        debugLogger.error("DownloaderService", "onUpdate " + staus + " " + progress)
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
        }
    }

    private fun stopDownloadAndNotifyUser(requestInfo: RequestInfo) {
        stopForeground(true)
        notificator.notifySpaceUnavailable(requestInfo)
        stopSelf()
    }

    override fun onRemoved(requestInfo: RequestInfo, id: Long) {
        checkIfDone()
    }

}

