package com.pietrantuono.podcasts.downloader.service

import com.pietrantuono.podcasts.interfaces.RealmEpisode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.tonyodev.fetch.request.RequestInfo


class CompletedDownloadsManager(
        private var episodesRepository: EpisodesRepository,
        private var podcastRepo: PodcastRepo) {

    fun onDownloadCompleted(requestInfo: RequestInfo) {
        episodesRepository.getEpisodeByEnclosureUrlSync(requestInfo.url)?.let {
            it.downloaded = true
            it.filePath = requestInfo.filePath
            episodesRepository.saveEpisodeSync(it as RealmEpisode)
            updatePodcast(it)
        }
    }

    private fun updatePodcast(episode: RealmEpisode) {
        val pdcast = podcastRepo.getPodcastByEpisodeSync(episode)
    }
}