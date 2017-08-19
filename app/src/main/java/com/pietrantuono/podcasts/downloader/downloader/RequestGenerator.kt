package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.apis.Episode
import com.tonyodev.fetch.request.Request

interface RequestGenerator {

    fun createRequest(url: String): Pair<Request, Episode>?

}