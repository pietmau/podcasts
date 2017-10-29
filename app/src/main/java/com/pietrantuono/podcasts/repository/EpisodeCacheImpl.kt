package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode

class EpisodeCacheImpl : EpisodeCache {
    private val episodesUrlsMap: MutableMap<String, Episode> = mutableMapOf()
    private val episodesEnclosuresUrlMap: MutableMap<String, Episode> = mutableMapOf()

    override fun getEpisodeByUrls(url: String): Episode? = episodesUrlsMap[url]

    override fun cacheEpisodeByUrls(url: String, episode: Episode) {
        episodesUrlsMap.put(url, episode)
    }

    override fun getEpisodesByEnclosureUrl(url: String): Episode? = episodesEnclosuresUrlMap[url]

    override fun cacheEpisodeByEnclosureUrl(url: String, episode: Episode) {
        episodesEnclosuresUrlMap.put(url, episode)
    }
}