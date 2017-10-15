package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode

class EpisodeCacheImpl : EpisodeCache {
    private val episodesUrlsMap: MutableMap<String, Episode> = mutableMapOf()
    private val episodesEnclosuresUrlMap: MutableMap<String, Episode> = mutableMapOf()

    override fun getEpisodeByUrls(url: String): Episode? = episodesUrlsMap[url]

    override fun cacheEpisodeByUrls(url: String, episode: RealmEpisode) {
        episodesUrlsMap.put(url, episode)
    }

    override fun getEpisodesByEnclosureUrl(url: String): Episode? = episodesEnclosuresUrlMap[url]

    override fun cacheEpisodeByEnclosureUrl(url: String, episode: RealmEpisode) {
        episodesEnclosuresUrlMap.put(url, episode)
    }
}