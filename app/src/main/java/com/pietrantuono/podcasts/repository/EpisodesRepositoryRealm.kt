package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable
import rx.Scheduler

class EpisodesRepositoryRealm(private val realm: Realm, private val ioScheduler: Scheduler) : EpisodesRepository {
    private val LINK = "link"
    private val episodesUrlsMap: MutableMap<String, Episode> = mutableMapOf()
    private val episodesEnclosuresUrlMap: MutableMap<String, Episode> = mutableMapOf()

    override fun getEpisodeByUrlAsync(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java)
                .equalTo(LINK, url)
                .findFirst()
                .asObservable<RealmEpisode>()
    }

    override fun onDownloadCompleted(episode: Episode?) {
        episode ?: return
        realm.executeTransaction {
            episode.downloaded = true
        }
    }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByUrlSync(url: String?): Episode? = url?.let { url ->
        episodesUrlsMap
                .get(url) ?: realm.where(RealmEpisode::class.java)
                .equalTo(LINK, url)
                .findFirst()
                ?.also { reamlEpisode ->
                    val episode = realm.copyFromRealm(reamlEpisode)
                    episodesUrlsMap.put(url, episode)
                }
    }

    override fun getEpisodeByUrlAsObservable(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java)
                .equalTo(LINK, url)
                .findFirst()
                .asObservable<RealmEpisode>()
                .filter { it != null }
                .map { it as RealmEpisode }
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
    }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByEnclosureUrlSync(url: String): Episode? =
            episodesEnclosuresUrlMap.get(url) ?: realm.where(RealmEpisode::class.java)
                    .contains("syndEnclosures.url", url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                        episodesEnclosuresUrlMap.put(url, episode)
                    }

}