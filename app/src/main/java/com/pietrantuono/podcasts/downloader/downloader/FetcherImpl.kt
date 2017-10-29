package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.pietrantuono.podcasts.downloader.service.CompletedDownloadsManager
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

class FetcherImpl(
        context: Context,
        private val repository: EpisodesRepository,
        private val requestManager: RequestManager,
        private var completedDownloadsManager: CompletedDownloadsManager) : Fetcher {

    private val fetch: Fetch

    init {
        fetch = Fetch.newInstance(context)
        fetch.setConcurrentDownloadsLimit(1)
    }

    override fun addListener(listner: FetchListener) {
        fetch.addFetchListener(listner)
    }

    override fun removeListener(listener: FetchListener) {
        fetch.removeFetchListener(listener)
    }

    override fun enqueueRequest(request: Request): Pair<Long, RequestInfo?> {
        val id = fetch.enqueue(request)
        val info = fetch[id]
        return Pair(id, info)
    }

    override fun alreadyDownloaded(url: String): Boolean {
        return fetch.get().filter { info -> info.url.equals(url, true) }.size > 0
    }

    override fun getRequestById(id: Long) = requestManager.getRequestById(id) ?: fetch[id]

    override fun download(url: String): Pair<Long, RequestInfo?>? {
        if (!alreadyDownloaded(url) && !episodeIsDownloaded(url)) {
            requestManager.createRequest(url)?.let { request ->
                val pair = enqueueRequest(request)
                requestManager.cacheRequest(pair)
                return pair
            }
        }
        return null
    }

    fun episodeIsDownloaded(url: String): Boolean {
        val episode = repository.getEpisodeByUrlSync(url)
        return episode != null && episode.downloaded
    }

    override fun stopDownload(requestInfo: RequestInfo) {
        fetch.remove(requestInfo.id)
    }

    override fun stopDownload() {
        fetch.removeAll()
    }

    override fun onDownloadCompleted(id: Long) {
        getRequestById(id)?.let { completedDownloadsManager.onDownloadCompleted(it) }
    }
}
