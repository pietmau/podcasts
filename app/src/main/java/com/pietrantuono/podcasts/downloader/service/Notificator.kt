package com.pietrantuono.podcasts.downloader.service

import com.pietrantuono.podcasts.apis.PodcastEpisode

interface Notificator {
    fun notifyProgress(episode: PodcastEpisode?, id: Long, progress: Int)

}