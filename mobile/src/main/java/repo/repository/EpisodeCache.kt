package repo.repository

import pojos.Episode
import pojos.RealmEpisode

interface EpisodeCache {
    fun getEpisodeByUrls(url: String): Episode?
    fun cacheEpisodeByUrls(url: String, episode: RealmEpisode)
    fun getEpisodesByEnclosureUrl(url: String): Episode?
    fun cacheEpisodeByEnclosureUrl(url: String, episode: RealmEpisode)
}