package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.repository.repository.Repository
import rx.Observer
import rx.subscriptions.CompositeSubscription

class SingleSubscribedModelImpl(val repository: Repository) : SingleSubscribedModel() {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun subscribe(trackId: Int, observer: Observer<in Podcast>) {
        val observable = repository.getPodcastById(trackId)
        compositeSubscription.add(observable.subscribe(observer))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }
}



