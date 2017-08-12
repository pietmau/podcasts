package com.pietrantuono.podcasts.downloader.downloader

import com.rometools.rome.feed.synd.SyndEnclosure
import com.tonyodev.fetch.request.Request
import javax.inject.Inject


class RequestGeneratorImpl @Inject constructor() : RequestGenerator {

    override fun createRequest(enclosure: SyndEnclosure): Request? = enclosure.url?.let { Request(it) }

}

