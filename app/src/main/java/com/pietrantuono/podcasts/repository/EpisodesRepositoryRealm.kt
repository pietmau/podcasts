package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable

class EpisodesRepositoryRealm(private val cache: EpisodeCache) : EpisodesRepository {
    private val LINK = "link"
    private val ENCLOSURE_URL = "syndEnclosures.url"

    override fun getEpisodeByUrlAsync(url: String): Observable<out Episode> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(RealmEpisode::class.java)
                        .equalTo(LINK, url)
                        .findFirst()
                        .asObservable<RealmEpisode>()
            }

    /** To be used from another Thread or from a service in another process . */
    override fun saveEpisodeSync(episode: RealmEpisode) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.copyToRealmOrUpdate(episode)
            }
        }
    }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByUrlSync(url: String?): Episode? = url?.let { url ->
        cache.getEpisodeByUrls(url) ?: Realm.getDefaultInstance().use { realm ->
            realm.where(RealmEpisode::class.java)
                    .equalTo(LINK, url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                        cache.cacheEpisodeByUrls(url, episode)
                    }
        }
    }

    override fun getEpisodeByUrlAsObservable(url: String): Observable<out Episode> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(RealmEpisode::class.java)
                        .equalTo(LINK, url)
                        .findFirst()
                        .asObservable<RealmEpisode>()
                        .filter { it != null }
                        .map { it as RealmEpisode }
                        .filter { it.isLoaded && it.isValid }
                        .map { realm.copyFromRealm(it) }
            }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByEnclosureUrlSync(url: String?): Episode? = url?.let { url ->
        cache.getEpisodesByEnclosureUrl(url) ?: Realm.getDefaultInstance().use { realm ->
            realm.where(RealmEpisode::class.java)
                    .contains(ENCLOSURE_URL, url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                        cache.cacheEpisodeByEnclosureUrl(url, episode)
                    }
        }
    }

}
