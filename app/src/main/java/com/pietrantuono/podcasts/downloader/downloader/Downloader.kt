package com.pietrantuono.podcasts.downloader.downloader

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast


interface Downloader {
    fun downloadIfAppropriate(podcast: Podcast?)
    fun downloadEpisodeFromLink(link: String)
}