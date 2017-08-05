package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.repository.repository.Repository
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import rx.Observer
import rx.subscriptions.CompositeSubscription

class SingleSubscribedModelImpl(val repository: Repository) : SingleSubscribedModel() {

    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    private var feed: Podcast? = null

    override fun subscribe(trackId: Int, observer: Observer<in Podcast>) {
        val observable = repository.getPodcastById(trackId)
        compositeSubscription.add(observable.subscribe(observer))
        compositeSubscription.add(observable.subscribe(object : SimpleObserver<Podcast>() {
            override fun onNext(feed: Podcast?) {
                this@SingleSubscribedModelImpl.feed = feed
            }
        }))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }
}



