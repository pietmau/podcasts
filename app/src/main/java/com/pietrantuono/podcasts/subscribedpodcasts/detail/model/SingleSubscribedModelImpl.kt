package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import rx.Observer
import rx.Subscription


class SingleSubscribedModelImpl(val realm: Realm) : SingleSubscribedModel() {
    private var subhscription: Subscription? = null

    override fun subscribe(trackId: String?, observer: Observer<SinglePodcastRealm>) {
        if (trackId == null) {
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



