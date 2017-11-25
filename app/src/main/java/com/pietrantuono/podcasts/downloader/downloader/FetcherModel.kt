package com.pietrantuono.podcasts.downloader.downloader

import repo.repository.EpisodesRepository
import javax.inject.Inject


class FetcherModel @Inject constructor(
        private var episodesRepo: EpisodesRepository) {

    fun episodeIsDownloadedSync(url: String): Boolean {
        val episode = episodesRepo.getEpisodeByUrlSync(url)
        return episode != null && episode.downloaded
    }

    fun setEpisodeNotDownloaded(id: Long) {
        episodesRepo.setEpisodeNotDownloadedSync(id)
    }
}