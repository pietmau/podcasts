package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

class FetcherImpl(
        context: Context,
        private val provider: DirectoryProvider) : Fetcher {

    private val fetch: Fetch

    init {
        fetch = Fetch.newInstance(context)
        fetch.setConcurrentDownloadsLimit(1)
    }

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = provider.thereIsEnoughSpace(fileSize)

    override fun addListener(listner: FetchListener) {
        fetch.addFetchListener(listner)
    }

    override fun removeListener(listener: FetchListener) {
        fetch.removeFetchListener(listener)
    }

    override fun enqueueRequest(request: Request): Pair<Long, RequestInfo?> {
        val id = fetch.enqueue(request)
        val info = fetch[id]
        return Pair(id,info)
    }

    override fun alreadyDownloaded(url: String): Boolean {
        return fetch.get().filter { info -> info.url.equals(url, true) }.size > 0
    }

    override fun getRequestById(id: Long) = fetch[id]
}
