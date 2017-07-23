package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import rx.Observer
import rx.Subscription


class SingleSubscribedModelImpl(val realm: Realm) : SingleSubscribedModel() {
    private var subhscription: Subscription? = null

    override fun subscribe(podcast: SinglePodcast?, observer: Observer<SinglePodcastRealm>) {
        subhscription = realm
                .where(SinglePodcastRealm::class.java)
                .equalTo("trackId", podcast?.trackId)
                .findFirstAsync()
                .asObservable<SinglePodcastRealm>()
                .cache()
                .subscribe(observer)
    }

    override fun unsubscribe() {
        if (subhscription != null && !(subhscription!!.isUnsubscribed)) {
            subhscription!!.unsubscribe()
        }
    }

}



