package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.request.Request

interface RequestGenerator {

    fun createRequest(url: String): Request?

}