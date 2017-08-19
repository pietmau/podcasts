package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.interfaces.RealmEpisode
import io.realm.Realm

class EpisodesRepositoryRealm(private val realm: Realm) : EpisodesRepository {

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