package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.request.RequestInfo

interface Fetcher {
    val allDone: Boolean

    fun getRequestById(id: Long): RequestInfo?
    fun download(title: String): Pair<Long, RequestInfo?>?
    fun stopDownload(requestInfo: RequestInfo)
    fun shutDown()
    fun onDownloadCompleted(requestInfo: Long, downloadedBytes: Long)
    fun deleteEpisode(id: Long)
    fun addCallback(downloaderService: Callback)

    interface Callback {
        fun onDownloadCompleted(it: RequestInfo)
        fun onUpdate(requestInfo: RequestInfo, status: Int, progress: Int, fileSize: Long)
        fun onRemoved(requestInfo: RequestInfo, id: Long)
    }

    fun setEpisodeDeletedAndNotDownloaded(id: Long)
}