package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface Fetcher {
    fun addListener(listner: FetchListener)
    fun removeListener(listener: FetchListener)
    fun enqueueRequest(request: Request): Pair<Long, RequestInfo?>
    fun alreadyDownloaded(url: String): Boolean
    fun getRequestById(id: Long): RequestInfo?
    fun download(url: String): Pair<Long, RequestInfo?>?
    fun stopDownload(requestInfo: RequestInfo)
    fun stopDownload()
    fun onDownloadCompleted(requestInfo: Long, downloadedBytes: Long)
}