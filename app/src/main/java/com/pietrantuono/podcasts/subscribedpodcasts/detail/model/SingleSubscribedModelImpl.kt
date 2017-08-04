package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import io.realm.Realm
import rx.Observer
import rx.subscriptions.CompositeSubscription

class SingleSubscribedModelImpl(val realm: Realm) : SingleSubscribedModel() {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    private var feed: SinglePodcast? = null

    override fun subscribe(trackId: Int, observer: Observer<in SinglePodcast>) {
        val observable = realm
                .where(SinglePodcastRealm::class.java)
                .equalTo("trackId", trackId)
                .findFirstAsync()
                .asObservable<SinglePodcastRealm>()
                .filter(SinglePodcastRealm::isLoaded)
                .map(realm::copyFromRealm)
                .cache()
        compositeSubscription.add(observable.subscribe(observer))
        compositeSubscription.add(observable.subscribe(object : SimpleObserver<SinglePodcast>() {
            override fun onNext(feed: SinglePodcast?) {
                this@SingleSubscribedModelImpl.feed = feed
            }
        }))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }
}



