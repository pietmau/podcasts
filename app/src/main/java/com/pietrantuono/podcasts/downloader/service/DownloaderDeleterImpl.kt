package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.request.RequestInfo

class DownloaderDeleterImpl(
        private val fetcher: Fetcher,
        private var notificator: DownloadNotificator,
        private var networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager) : DowloaderDeleter {

    override fun ifRemovedDelete(info: RequestInfo, status: Int) {
        fetcher.setEpisodeDeletedAndNotDownloaded(info.id)
    }

    override val allDone: Boolean
        get() = fetcher.allDone

    override fun shutDown() {
        fetcher.shutDown()
    }

    override fun deleteAllEpisodes(intent: Intent) {
        val ids = intent.getSerializableExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST) as? ArrayList<Long>
        ids?.filter { it >= 0 }?.forEach { deleteEpisode(it) }
    }

    override fun downloadAllEpisodes(intent: Intent) {
        if (shouldDownload()) {
            intent.getStringArrayListExtra(EXTRA_TRACK_LIST)?.let { enqueueEpisodes(it) }
        }
    }

    override fun downloadEpisode(intent: Intent): Pair<Long, RequestInfo?>? {
        if (shouldDownload()) {
            intent.getStringExtra(EXTRA_TRACK)?.let {
                return getAndEnqueueSingleEpisode(it)
            }
        }
        return null
    }

    override fun addCallback(callback: Fetcher.Callback) {
        fetcher.addCallback(callback)
    }

    override fun deleteEpisode(id: Long) {
        fetcher.deleteEpisode(id)
        notificator.broadcastDeleteEpisode(id)
    }

    private fun getAndEnqueueSingleEpisode(uri: String): Pair<Long, RequestInfo?>? {
        return fetcher.download(uri)
    }

    private fun enqueueEpisodes(list: List<String>) {
        for (uri in list) {
            if (!thereIsEnoughDiskSpace(0)) {
                notificator.notifySpaceUnavailable(uri)
                break
            }
            getAndEnqueueSingleEpisode(uri)
        }
    }

    internal fun shouldDownload() = networkDiskAndPreferenceManager.shouldDownload

    override fun thereIsEnoughDiskSpace(fileSize: Long) = networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)
}