package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.interfaces.RealmPodcastEpisode
import io.realm.Realm

class EpisodesRepositoryRealm(private val realm: Realm) : EpisodesRepository {

    override fun getEpisoceById(trackId: String?): PodcastEpisode? {
        return realm.where(RealmPodcastEpisode::class.java).equalTo("trackId", trackId).findFirst()
    }

}