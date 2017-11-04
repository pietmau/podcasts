package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.apis.Episode
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface Fetcher {
    fun enqueueRequest(request: Request): Pair<Long, RequestInfo?>
    fun alreadyDownloaded(url: String): Boolean
    fun getRequestById(id: Long): RequestInfo?
    fun download(url: String): Pair<Long, RequestInfo?>?
    fun stopDownload(requestInfo: RequestInfo)
    fun shutDown()
    fun onDownloadCompleted(requestInfo: Long, downloadedBytes: Long)
    fun deleteEpisode(episode: Episode)
    fun addCallback(downloaderService: Callback)

    interface Callback {
        fun onDownloadCompleted()
        fun onUpdate(requestInfo: RequestInfo, progress: Int, fileSize: Long)
    }
}