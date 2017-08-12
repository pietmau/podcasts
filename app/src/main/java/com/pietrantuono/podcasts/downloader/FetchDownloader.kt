package com.pietrantuono.podcasts.downloader

import android.content.Context
import com.tonyodev.fetch.Fetch
import com.tonyodev.fetch.listener.FetchListener
import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo

class FetchDownloader(context: Context) : Downloader {
    private val fetch: Fetch

    init {
        fetch = Fetch.newInstance(context)
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
}