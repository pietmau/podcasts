package com.pietrantuono.podcasts.downloader.service

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.tonyodev.fetch.request.RequestInfo


class CompletedDownloadsManager(
        private var episodesRepo: EpisodesRepository,
        private var podcastRepo: PodcastRepo) {

    fun onDownloadCompleted(requestInfo: RequestInfo) {
        episodesRepo.getEpisodeByEnclosureUrlSync(requestInfo.url)?.let {
            it.downloaded = true
            it.filePath = requestInfo.filePath
            episodesRepo.saveEpisodeSync(it)
            updatePodcast(it)
        }
    }

    private fun updatePodcast(episode: Episode) {
        val podcast = podcastRepo.getPodcastByEpisodeSync(episode)
        // Nice!
        val dowloadedCount = podcast?.episodes?.count { it.downloaded }
        dowloadedCount?.let {
            podcast.numberOfEpisodesDowloaded = it
            podcastRepo.savePodcastSync(podcast)
        }
    }
}