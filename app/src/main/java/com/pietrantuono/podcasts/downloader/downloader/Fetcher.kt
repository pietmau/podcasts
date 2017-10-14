package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface Fetcher {
    fun addListener(listner: FetchListener)
    fun removeListener(listener: FetchListener)
    fun enqueueRequest(request: Request): Pair<Long, RequestInfo?>
    fun alreadyDownloaded(url: String): Boolean
    fun thereIsEnoughSpace(fileSize: Long): Boolean
    fun getRequestById(id: Long): RequestInfo?
}