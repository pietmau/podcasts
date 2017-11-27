package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.Episode
import models.pojos.Podcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(message: MessageCreator.AlertMessage, link: String)
    fun confirmDownloadAllEpisodes(message: MessageCreator.AlertMessage, trackId: Podcast)
    fun confirmDeleteEpisode(episode: MessageCreator.AlertMessage, link: Episode)
    fun confirmDeleteAllEpisodes(episodes: MessageCreator.AlertMessage, podcast: Podcast)
}