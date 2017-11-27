package com.pietrantuono.podcasts.subscribedpodcasts.model


import models.pojos.Podcast
import repo.repository.PodcastRepo
import rx.Observable
import rx.Observer
import rx.Subscription

class SubscribedPodcastModelImpl(private val repository: PodcastRepo) : SubscribedPodcastModel {
    private var subscription: Subscription? = null
    private var observable: Observable<List<Podcast>>? = null

    override fun subscribeToSubscribedPodcasts(observer: Observer<List<Podcast>>) {
        if (observable == null) {
            observable = repository.getSubscribedPodcasts()
        }
        subscription = observable?.subscribe(observer)
    }

    override fun unsubscribe() {
        subscription?.unsubscribe()
    }
}
