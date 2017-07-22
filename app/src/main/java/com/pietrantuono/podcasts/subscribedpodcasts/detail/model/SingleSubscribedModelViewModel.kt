package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import android.arch.lifecycle.LiveData
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastEpisodeModel
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import io.realm.RealmObject
import rx.Observer

object SingleSubscribedModelViewModel : SingleSubscribedModel, LiveData<List<PodcastEpisodeModel>>() {
    private val realm = Realm.getDefaultInstance()

    override fun subscribeToFeed(observer: Observer<PodcastFeed>, podcast: SinglePodcast) {
        realm.where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirstAsync()
                .asObservable<RealmObject>().cache().map{ toPodcastFeed(it) }.subscribe(observer)
    }

    private fun toPodcastFeed(obj: RealmObject?): PodcastFeed {

    }

}



