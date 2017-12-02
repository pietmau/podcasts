package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.pietrantuono.podcasts.downloader.service.CompletedDownloadsManager
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.exception.NotUsableException
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.RequestInfo

class FetcherImpl(
        context: Context,
        private val fetcherModel: FetcherModelImpl,
        private val requestManager: RequestManager,
        private var completedDownloadsManager: CompletedDownloadsManager) : Fetcher, FetchListener {

    private val fetch: Fetch

    private var callback: Fetcher.Callback? = null

    init {
        fetch = Fetch.newInstance(context)
        fetch.setConcurrentDownloadsLimit(1)
        fetch.addFetchListener(this)
    }

    override fun addCallback(callback: Fetcher.Callback) {
        this.callback = callback
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        getRequestById(id)?.let {
            callback?.onUpdate(it, progress, fileSize)
        }
        if (progress >= DownloaderService.DOWNLOAD_COMPLETED) {
            onDownloadCompleted(id, downloadedBytes)
        }
    }

    override fun getRequestById(id: Long) = requestManager.getRequestById(id) ?: fetch[id]

    override fun download(uri: String): Pair<Long, RequestInfo?>? {
        if (!alreadyDowloanded(uri)) {
            requestManager.createRequestForDownload(uri)?.let { request ->
                val pair = fetch.enqueueRequest(request)
                requestManager.cacheRequest(pair)
                return pair
            }
        }
        return null
    }

    private fun alreadyDowloanded(uri: String): Boolean {
        val link = fetcherModel.getEpisodeSync(uri)?.link
        if (link == null) {
            return false
        }
        return fetch.alreadyDownloaded(link) || fetcherModel.episodeIsDownloadedSync(uri)
    }

    override fun stopDownload(requestInfo: RequestInfo) {
        fetch.removeRequest(requestInfo.id)
    }

    override fun shutDown() {
        callback = null
        try {
            fetch.shutDown()
        } catch (exception: NotUsableException) {
        }
    }

    override fun onDownloadCompleted(id: Long, downloadedBytes: Long) {
        getRequestById(id)?.let {
            completedDownloadsManager.onDownloadCompleted(it, downloadedBytes)
            callback?.onDownloadCompleted(it)
        }
    }

    override fun deleteEpisode(id: Long) {
        fetch.remove(id)
        fetcherModel.setEpisodeNotDownloaded(id)
    }
}

