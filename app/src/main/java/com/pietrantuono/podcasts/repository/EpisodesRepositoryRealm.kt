package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.interfaces.RealmPodcastEpisode
import io.realm.Realm

class EpisodesRepositoryRealm(private val realm: Realm) : EpisodesRepository {

    override fun getEpisodeByUrl(url: String?): PodcastEpisode? {
        return url?.let { realm.where(RealmPodcastEpisode::class.java).equalTo("link", url).findFirst() } ?: null
    }


}