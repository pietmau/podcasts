package com.pietrantuono.podcasts.downloader.service

import com.tonyodev.fetch.request.RequestInfo
import pojos.PodcastRealm
import pojos.RealmEpisode
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo


class CompletedDownloadsManager(
        private var episodesRepo: EpisodesRepository,
        private var podcastRepo: PodcastRepo) {

    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long) {
        episodesRepo.onDownloadCompleted(requestInfo,downloadedBytes)
    }

    private fun updatePodcast(episode: RealmEpisode) {
        val podcast = podcastRepo.getPodcastByEpisodeSync(episode) as? PodcastRealm
        // Nice!
        val dowloadedCount = podcast?.episodes?.count { it.downloaded }
        dowloadedCount?.let {
            podcast.numberOfEpisodesDowloaded = it
            podcastRepo.savePodcastSync(podcast)
        }
    }
}