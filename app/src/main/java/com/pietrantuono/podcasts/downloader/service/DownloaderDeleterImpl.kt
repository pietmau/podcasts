package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.request.RequestInfo
import hugo.weaving.DebugLog

class DownloaderDeleterImpl(
        private val fetcher: Fetcher,
        private val notificator: DownloadNotificator,
        private val networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager,
        private val debugLogger: DebugLogger
) : DowloaderDeleter {

    private val TAG = "DownloaderDeleterImpl"

    override val allDone: Boolean
        get() = fetcher.allDone

    override fun shutDown() {
        fetcher.shutDown()
    }

    @DebugLog
    override fun deleteAllEpisodes(intent: Intent) {
        val ids = intent.getSerializableExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST) as? ArrayList<Long>
        ids?.filter { it >= 0 }?.forEach { deleteEpisode(it) }
    }

    @DebugLog
    override fun downloadAllEpisodes(intent: Intent) {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "downloadAllEpisodes START")
        if (shouldDownload()) {
            intent.getStringArrayListExtra(EXTRA_TRACK_LIST)?.let { enqueueEpisodes(it) }
        }
        debugLogger.error(TAG, "downloadAllEpisodes END  " + (System.currentTimeMillis() - time))
    }

    override fun downloadEpisode(intent: Intent): Pair<Long, RequestInfo?>? {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "downloadEpisode START")
        if (shouldDownload()) {
            intent.getStringExtra(EXTRA_TRACK)?.let {
                debugLogger.error(TAG, "downloadEpisode END  " + (System.currentTimeMillis() - time))
                return getAndEnqueueSingleEpisode(it)
            }
        }
        debugLogger.error(TAG, "downloadEpisode END  " + (System.currentTimeMillis() - time))
        return null
    }

    override fun addCallback(callback: Fetcher.Callback) {
        fetcher.addCallback(callback)
    }

    @DebugLog
    override fun deleteEpisode(id: Long) {
        fetcher.deleteEpisode(id)
        notificator.broadcastDeleteEpisode(id)
    }

    @DebugLog
    private fun getAndEnqueueSingleEpisode(uri: String): Pair<Long, RequestInfo?>? {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "getAndEnqueueSingleEpisode START")
        val result = fetcher.download(uri)
        debugLogger.error(TAG, "getAndEnqueueSingleEpisode END  " + (System.currentTimeMillis() - time))
        return result
    }

    @DebugLog
    private fun enqueueEpisodes(list: List<String>) {
        val time = System.currentTimeMillis()
        debugLogger.error(TAG, "enqueueEpisodes START")
        for (uri in list) {
            if (!thereIsEnoughDiskSpace(0)) {
                notificator.notifySpaceUnavailable(uri)
                break
            }
            getAndEnqueueSingleEpisode(uri)
        }
        debugLogger.error(TAG, "enqueueEpisodes END  " + (System.currentTimeMillis() - time))
    }

    internal fun shouldDownload() = networkDiskAndPreferenceManager.shouldDownload

    override fun thereIsEnoughDiskSpace(fileSize: Long) = networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)
}