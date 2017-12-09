package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(message: MessageCreator.AlertMessage, link: String)
    fun confirmDownloadAllEpisodes(message: MessageCreator.AlertMessage, trackId: DownloadedPodcast?)
    fun confirmDeleteEpisode(episode: MessageCreator.AlertMessage, link: DownloadedEpisode?)
    fun confirmDeleteAllEpisodes(episodes: MessageCreator.AlertMessage, podcast: DownloadedPodcast?)
    fun updateItem(i: Int, j: Int, episode: DownloadedEpisode)
}