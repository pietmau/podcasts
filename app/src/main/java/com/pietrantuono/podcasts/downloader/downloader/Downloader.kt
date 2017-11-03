package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode


interface Downloader {
    fun downloadIfAppropriate(podcast: Podcast?)
    fun downloadEpisodeFromLink(link: String)
    fun deleteEpisode(episode: Episode)
}