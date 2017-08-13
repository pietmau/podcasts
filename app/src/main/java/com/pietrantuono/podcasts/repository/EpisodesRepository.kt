package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.PodcastEpisode

interface EpisodesRepository {
    fun getEpisodeByUrl(url: String?): PodcastEpisode?
    fun onDownloadCompleted(episode: PodcastEpisode?)
}