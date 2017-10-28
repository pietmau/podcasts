package com.pietrantuono.podcasts.downloadfragment.view

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


interface DownloadView {
    fun setPodcasts(list: List<DownloadedPodcast>)
    fun confirmDownloadEpisode(episode: DownloadedEpisode?)
    fun confirmDownloadEpisodes(episodes: List<DownloadedEpisode>?)
    fun confirmDeleteEpisode(episode: DownloadedEpisode?)
    fun confirmDeleteEpisodes(episodes: List<DownloadedEpisode>?)
}