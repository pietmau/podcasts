package com.pietrantuono.podcasts.downloader.downloader

import pojos.Episode
import pojos.Podcast


interface Downloader {
    fun downloadIfAppropriate(podcast: Podcast?)
    fun downloadEpisodeFromLink(link: String)
    fun deleteEpisode(episode: Episode)
    fun downLoadAllEpisodes(episodes: List<Episode>)
    fun deleteAllEpisodes(podcast: Podcast)
}