package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(message: MessageCreator.AlertMessage, link: String)
    fun confirmDownloadAllEpisodes(message: MessageCreator.AlertMessage, trackId: Podcast)
    fun confirmDeleteEpisode(episode: MessageCreator.AlertMessage, link: Episode)
    fun confirmDeleteEpisodes(episodes: String)
}