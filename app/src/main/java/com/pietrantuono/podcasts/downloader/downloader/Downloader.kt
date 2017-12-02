package com.pietrantuono.podcasts.downloader.downloader

import models.pojos.Episode
import models.pojos.Podcast


interface Downloader {
    fun downloadIfAppropriate(podcast: Podcast?)
    fun downloadEpisodeFromUri(uri: String)
    fun deleteEpisode(episode: Episode)
    fun downLoadAllEpisodes(episodes: List<Episode>)
    fun deleteAllEpisodes(podcast: Podcast)
    fun downloadAndPlayFromUri(uri: String)
}