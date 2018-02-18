package com.pietrantuono.podcasts.downloader.downloader

import com.tonyodev.fetch.request.RequestInfo
import repository.EpisodesRepository
import javax.inject.Inject


class FetcherModelImpl @Inject constructor(
        private var episodesRepo: EpisodesRepository) {

    fun episodeIsDownloadedSync(uri: String): Boolean {
        val episode = episodesRepo.getEpisodeByUriSync(uri)
        return episode != null && episode.downloaded
    }

    fun setEpisodeDeletedAndNotDownloaded(id: Long) {
        episodesRepo.setEpisodeDeletedAndNotDowloaded(id)
    }

    fun saveRequestId(uri: String, requestId: Long) {
        if (requestId >= 0) {
            episodesRepo.saveRequestId(uri, requestId)
        }
    }

    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long) {
        episodesRepo.onDownloadCompleted(requestInfo, downloadedBytes)
    }

}