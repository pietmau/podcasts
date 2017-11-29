package com.pietrantuono.podcasts.downloader.downloader

import models.pojos.Episode
import repo.repository.EpisodesRepository
import javax.inject.Inject


class FetcherModelImpl @Inject constructor(
        private var episodesRepo: EpisodesRepository) {

    fun episodeIsDownloadedSync(title: String): Boolean {
        val episode = episodesRepo.getEpisodeByTitleSync(title)
        return episode != null && episode.downloaded
    }

    fun setEpisodeNotDownloaded(id: Long) {
        episodesRepo.setEpisodeNotDownloadedSync(id)
    }

    fun getEpisodeSync(titl: String): Episode? = episodesRepo.getEpisodeByTitleSync(titl)

}