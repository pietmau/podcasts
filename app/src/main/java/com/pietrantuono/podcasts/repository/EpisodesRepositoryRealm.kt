package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable
import rx.Scheduler

class EpisodesRepositoryRealm(private val realm: Realm, private val ioScheduler: Scheduler) : EpisodesRepository {
    private val LINK = "link"
    private val cache: MutableMap<String, Episode> = mutableMapOf()

    override fun getEpisodeByUrlAsync(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java).equalTo(LINK, url).findFirst().asObservable<RealmEpisode>()
    }

    override fun onDownloadCompleted(episode: Episode?) {
        if (episode == null) return
        realm.executeTransaction {
            episode.downloaded = true
        }
    }

    override fun getEpisodeByUrl(url: String?): Episode? = url?.let { url ->
        cache.get(url) ?: realm
                .where(RealmEpisode::class.java)
                .equalTo(LINK, url)
                .findFirst()
                ?.also { reamlEpisode ->
                    val episode = realm.copyFromRealm(reamlEpisode)
                    cache.put(url, episode)
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
}