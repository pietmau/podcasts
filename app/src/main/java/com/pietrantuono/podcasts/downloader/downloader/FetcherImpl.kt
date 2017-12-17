package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.service.*
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.exception.NotUsableException
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.RequestInfo

class FetcherImpl(
        private val fetch: Fetch,
        private val fetcherModel: FetcherModelImpl,
        private val requestManager: RequestManager,
        private var completedDownloadsManager: CompletedDownloadsManager,
        private var debugLogger: DebugLogger
) : Fetcher, FetchListener {

    override val allDone: Boolean
        get() = checkIfDone()

    private fun checkIfDone(): Boolean = fetch.get().filter { isDowloadingOrQueued(it) }.count() <= 0

    private fun isDowloadingOrQueued(it: RequestInfo): Boolean {
        if (it.status == STATUS_DOWNLOADING) {
            return true
        }
        if (it.status == STATUS_QUEUED) {
            return true
        }
        return false
    }

    private var callback: Fetcher.Callback? = null

    init {
        fetch.addFetchListener(this)
    }

    override fun addCallback(callback: Fetcher.Callback) {
        this.callback = callback
    }

    override fun onUpdate(id: Long, status: Int, progress: Int, downloadedBytes: Long, fileSize: Long, error: Int) {
        updateCallback(id, status, progress, fileSize)
        checkIfCompleted(progress, id, downloadedBytes)
        checkIfRemoved(id, status)
    }

    private fun checkIfRemoved(id: Long, status: Int) {
        if (status == STATUS_REMOVED) {
            getRequestById(id)?.let {
                callback?.onRemoved(it, id)
            }
        }
    }

    private fun checkIfCompleted(progress: Int, id: Long, downloadedBytes: Long) {
        if (progress >= DOWNLOAD_COMPLETED) {
            onDownloadCompleted(id, downloadedBytes)
        }
    }

    private fun updateCallback(id: Long, status: Int, progress: Int, fileSize: Long) {
        debugLogger.debug("FetcherImpl", "onUpdate " + status + " " + progress)
        getRequestById(id)?.let {
            callback?.onUpdate(it, status, progress, fileSize)
        }
    }

    override fun getRequestById(id: Long) = requestManager.getRequestById(id) ?: fetch[id]

    override fun download(uri: String): Pair<Long, RequestInfo?>? {
        if (!alreadyDowloanded(uri)) {
            requestManager.createRequestForDownload(uri)?.let { request ->
                val pair = fetch.enqueueRequest(request)
                val requestId = pair.first
                fetcherModel.saveRequestId(uri, requestId)
                requestManager.cacheRequest(pair)
                return pair
            }
        }
        return null
    }

    private fun alreadyDowloanded(uri: String): Boolean {
        return fetch.alreadyDownloaded(uri) || fetcherModel.episodeIsDownloadedSync(uri)
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
        if (!fetch.isValid) {
            return
        }
        fetch.remove(id)
    }

    override fun setEpisodeDeletedAndNotDownloaded(id: Long) {
        fetcherModel.setEpisodeDeletedAndNotDownloaded(id)
    }

}

