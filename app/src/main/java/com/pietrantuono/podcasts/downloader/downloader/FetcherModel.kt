package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.repository.EpisodesRepository
import javax.inject.Inject


class FetcherModel @Inject constructor(private val repo: EpisodesRepository) {

    fun episodeIsDownloadedSync(url: String): Boolean {
        val episode = repo.getEpisodeByUrlSync(url)
        return episode != null && episode.downloaded
    }
}