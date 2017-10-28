package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.presenter.MessageCreator
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(episode: MessageCreator.AlertMessage, link: String)
    fun confirmDownloadEpisodes(episodes: String)
    fun confirmDeleteEpisode(episode: String)
    fun confirmDeleteEpisodes(episodes: String)
}