package com.pietrantuono.podcasts.downloader.downloader

import com.rometools.rome.feed.synd.SyndEnclosure
import com.tonyodev.fetch.request.Request

interface RequestGenerator {

    fun createRequest(enclosure: SyndEnclosure): Request?

}