package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode

interface EpisodeCache {
    fun getEpisodeByUrls(url: String): Episode?
    fun cacheEpisodeByUrls(url: String, episode: Episode)
    fun getEpisodesByEnclosureUrl(url: String): Episode?
    fun cacheEpisodeByEnclosureUrl(url: String, episode: Episode)
}