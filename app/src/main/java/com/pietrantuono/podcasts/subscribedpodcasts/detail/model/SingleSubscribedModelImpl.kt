package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.Repository
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import rx.Observer
import rx.subscriptions.CompositeSubscription

class SingleSubscribedModelImpl(val repository: Repository) : SingleSubscribedModel() {

    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    private var feed: SinglePodcast? = null

    override fun subscribe(trackId: Int, observer: Observer<in SinglePodcast>) {
        val observable = repository.getPodcastById(trackId)
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



