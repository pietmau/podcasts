package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

interface Downloader {
    fun addListener(listner: FetchListener)
    fun removeListener(listener: FetchListener)
    fun getById(id: Long): RequestInfo?
    fun enqueueRequest(request: Request): Long
}