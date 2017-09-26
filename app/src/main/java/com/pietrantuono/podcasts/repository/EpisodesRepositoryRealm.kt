package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable
import rx.Scheduler

class EpisodesRepositoryRealm(private val realm: Realm, private val ioScheduler: Scheduler) : EpisodesRepository {

    override fun getEpisodeByUrlAsync(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java).equalTo("link", url).findFirst().asObservable<RealmEpisode>()
    }

    override fun onDownloadCompleted(episode: Episode?) {
        if (episode == null) return
        realm.executeTransaction {
            episode.downloaded = true
        }
    }

    override fun getEpisodeByUrl(url: String?): Episode? {
        return url?.let {
            var episode = realm.where(RealmEpisode::class.java).equalTo("link", url).findFirst()
            episode = episode as RealmEpisode
            episode = realm.copyFromRealm(episode)
            episode
        } ?: null
    }

    override fun getEpisodeByUrlAsObservable(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java)
                .equalTo("link", url)
                .findFirst()
                .asObservable<RealmEpisode>()
                .filter { it != null }
                .map { it as RealmEpisode }
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
    }

}