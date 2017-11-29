package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.request.RequestInfo

interface Fetcher {
    fun getRequestById(id: Long): RequestInfo?
    fun download(uri: String): Pair<Long, RequestInfo?>?
    fun stopDownload(requestInfo: RequestInfo)
    fun shutDown()
    fun onDownloadCompleted(requestInfo: Long, downloadedBytes: Long)
    fun deleteEpisode(id: Long)
    fun addCallback(downloaderService: Callback)

    interface Callback {
        fun onDownloadCompleted(it: RequestInfo)
        fun onUpdate(requestInfo: RequestInfo, progress: Int, fileSize: Long)
    }


}