package com.pietrantuono.podcasts.downloader

import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.tonyodev.fetch.request.Request
import javax.inject.Inject


class RequestGeneratorImpl @Inject constructor(): RequestGenerator {

    override fun createRequest(episode: PodcastEpisode): Request {
        return Request("",""",""")
    }
}

