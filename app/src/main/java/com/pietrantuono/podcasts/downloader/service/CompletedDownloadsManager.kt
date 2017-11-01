package com.pietrantuono.podcasts.downloader.service

import com.pietrantuono.podcasts.interfaces.RealmEpisode
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import com.tonyodev.fetch.request.RequestInfo


class CompletedDownloadsManager(
        private var episodesRepo: EpisodesRepository,
        private var podcastRepo: PodcastRepo) {

    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long) {
        episodesRepo.getEpisodeByEnclosureUrlSync(requestInfo.url)?.let {
            it.downloaded = true
            it.filePath = requestInfo.filePath
            it.fileSizeInBytes = downloadedBytes
            episodesRepo.saveEpisodeSync(it as RealmEpisode)
            updatePodcast(it)
        }
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