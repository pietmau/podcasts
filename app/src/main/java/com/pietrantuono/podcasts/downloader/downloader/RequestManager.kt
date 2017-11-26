package com.pietrantuono.podcasts.downloader.downloader


import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo
import diocan.pojos.Episode

interface RequestManager {
    fun createRequestForDownload(url: String): Request?
    fun getRequestById(id: Long): RequestInfo?
    fun cacheRequest(pair: Pair<Long, RequestInfo?>)
    fun createRequestForDeletion(episode: Episode): Request?

}