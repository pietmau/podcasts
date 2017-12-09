package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.Episode


interface Downloader {
    fun downloadIfAppropriate(podcast: DownloadedPodcast?)
    fun downloadEpisodeFromUri(uri: String)
    fun deleteEpisode(episode: DownloadedEpisode?)
    fun downLoadAllEpisodes(episodes: List<Episode>)
    fun deleteAllEpisodes(podcast: DownloadedPodcast?)
    fun downloadAndPlayFromUri(uri: String)
}