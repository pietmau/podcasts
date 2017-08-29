package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm
import rx.Observable
import rx.subjects.BehaviorSubject

class EpisodesRepositoryRealm(private val realm: Realm) : EpisodesRepository {
    val subject by lazy { BehaviorSubject.create<Episode>() }

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


}