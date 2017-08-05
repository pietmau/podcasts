package com.pietrantuono.podcasts.repository

import android.content.Context
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.providers.RealmUtlis
import com.pietrantuono.podcasts.repository.repository.RepoServices
import io.realm.Realm

class RepoServicesImpl(private val context: Context, private val realm:Realm) : RepoServices {

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast) {
        var singlePodcast = realm.where(PodcastRealm::class.java)
                .equalTo("trackId", podcast.trackId)
                .findFirst()
        if (singlePodcast == null) {
            singlePodcast = RealmUtlis.toSinglePodcastRealm(podcast)
        }
        realm.executeTransactionAsync {
            singlePodcast.isPodcastSubscribed = !singlePodcast.isPodcastSubscribed
            it.copyToRealmOrUpdate(singlePodcast)
        }
    }

}