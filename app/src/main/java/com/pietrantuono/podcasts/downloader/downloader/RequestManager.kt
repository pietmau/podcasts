package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface RequestManager {
    fun createRequest(url: String): Request?
    fun getRequestById(id: Long): RequestInfo?
    fun cacheRequest(pair: Pair<Long, RequestInfo?>)

}