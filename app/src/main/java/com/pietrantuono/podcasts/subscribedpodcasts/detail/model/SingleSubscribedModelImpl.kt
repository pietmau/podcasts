package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import rx.Observer
import rx.Subscription


class SingleSubscribedModelImpl(val realm: Realm) : SingleSubscribedModel() {
    private var subhscription: Subscription? = null

    override fun subscribe(trackId: Int, observer: Observer<SinglePodcastRealm>) {
        if (trackId == null || trackId < 0) {
            return
        }
        subhscription = realm
                .where(SinglePodcastRealm::class.java)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable<SinglePodcastRealm>()
                .filter(SinglePodcastRealm::isLoaded)
                .map(realm::copyFromRealm)
                .cache()
                .subscribe(observer)
    }

    override fun unsubscribe() {
        if (subhscription != null && !(subhscription!!.isUnsubscribed)) {
            subhscription!!.unsubscribe()
        }
    }

}



