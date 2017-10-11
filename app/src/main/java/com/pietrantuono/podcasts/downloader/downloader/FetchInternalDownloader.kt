package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

class FetchInternalDownloader(context: Context, private val provider: DirectoryProvider) : InternalDownloader {

    override fun thereIsEnoughSpace(fileSize: Long): Boolean = provider.thereIsEnoughSpace(fileSize)

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

    override fun getById(id: Long): RequestInfo? {
        return fetch.get(id)
    }

    override fun enqueueRequest(request: Request): Long {
        return fetch.enqueue(request)
    }

    override fun alreadyDownloaded(url: String): Boolean {
        return fetch.get().filter { info -> info.url.equals(url, true) }.size > 0
    }
}