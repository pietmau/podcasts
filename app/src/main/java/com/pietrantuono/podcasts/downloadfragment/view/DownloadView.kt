package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.Episode


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(message: MessageCreator.AlertMessage, link: String)
    fun confirmDownloadAllEpisodes(message: MessageCreator.AlertMessage, trackId: MutableList<DownloadedEpisode>)
    fun confirmDeleteEpisode(episode: MessageCreator.AlertMessage, link: Episode)
    fun confirmDeleteAllEpisodes(episodes: MessageCreator.AlertMessage, podcast: DownloadedPodcast?)
    fun updateItem(i: Int, j: Int, episode: DownloadedEpisode)
}