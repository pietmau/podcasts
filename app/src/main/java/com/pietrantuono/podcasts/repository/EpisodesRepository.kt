package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.PodcastEpisode

interface EpisodesRepository {
    fun getEpisoceById(trackId: String?): PodcastEpisode?

}