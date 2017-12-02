package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.downloader.downloader.Fetcher

class DownloaderDeleterImpl(
        private val fetcher: Fetcher,
        private var notificator: DownloadNotificator,
        private var networkDiskAndPreferenceManager: NetworkDiskAndPreferenceManager) : DowloaderDeleter {

    override fun shutDown() {
        fetcher.shutDown()
    }

    override fun deleteAllEpisodes(intent: Intent) {
        (intent.getSerializableExtra(EXTRA_DOWNLOAD_REQUEST_ID_LIST) as? ArrayList<Long>)?.forEach {
            deleteEpisode(it)
        }
    }

    override fun downloadAllEpisodes(intent: Intent) {
        if (shouldDownload()) {
            intent.getStringArrayListExtra(EXTRA_TRACK_LIST)?.let { enqueueEpisodes(it) }
        }
    }

    override fun downloadEpisode(intent: Intent) {
        if (shouldDownload()) {
            intent.getStringExtra(EXTRA_TRACK)?.let {
                getAndEnqueueSingleEpisode(it)
            }
        }
    }

    override fun addCallback(callback: Fetcher.Callback) {
        fetcher.addCallback(callback)
    }

    override fun deleteEpisode(id: Long) {
        fetcher.deleteEpisode(id)
        notificator.broadcastDeleteEpisode(id)
    }

    private fun getAndEnqueueSingleEpisode(uri: String) {
        fetcher.download(uri)
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

    override fun thereIsEnoughDiskSpace(fileSize: Long)= networkDiskAndPreferenceManager.thereIsEnoughSpace(fileSize)
}