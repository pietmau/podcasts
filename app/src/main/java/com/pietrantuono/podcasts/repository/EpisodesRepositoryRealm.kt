package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable
import rx.Scheduler
import rx.subjects.BehaviorSubject

class EpisodesRepositoryRealm(private val realm: Realm, private val ioScheduler: Scheduler) : EpisodesRepository {

    override fun getEpisodeByUrlAsync(url: String?): Observable<out Episode> {
        return realm.where(RealmEpisode::class.java).equalTo("link", url).findFirst().asObservable<RealmEpisode>()
    }

    override fun onDownloadCompleted(episode: Episode?) {
        if (episode == null) {
            return
        }
        realm.executeTransaction {
            episode.downloaded = true
        }
    }

    override fun getEpisodeByUrl(url: String?): Episode? {
        return url?.let { realm.where(RealmEpisode::class.java).equalTo("link", url).findFirst() } ?: null
    }

    override fun getEpisodeByUrlAsObservable(url: String?): Observable<out Episode> {
        return Observable
                .fromCallable { getEpisodeByUrl(url) }
                .filter { it != null }
                .map { it as RealmEpisode }
                .filter { it.isLoaded && it.isValid }
                .map { realm.copyFromRealm(it) }
                .subscribeOn(ioScheduler)
    }

}