package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode

interface EpisodeCache {
    fun getEpisodeByUrls(url: String): Episode?
    fun cacheEpisodeByUrls(url: String, episode: RealmEpisode)
    fun getEpisodesByEnclosureUrl(url: String): Episode?
    fun cacheEpisodeByEnclosureUrl(url: String, episode: RealmEpisode)
}