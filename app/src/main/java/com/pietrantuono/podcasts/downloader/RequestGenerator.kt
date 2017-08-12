package com.pietrantuono.podcasts.downloader

import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.tonyodev.fetch.request.Request

interface RequestGenerator {

    fun createRequest(episode: PodcastEpisode): Request

}