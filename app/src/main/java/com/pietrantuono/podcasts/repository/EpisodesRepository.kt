package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode

interface EpisodesRepository {
    fun getEpisodeByUrl(url: String?): Episode?
    fun onDownloadCompleted(episode: Episode?)
}