package com.pietrantuono.podcasts.subscribedpodcasts.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.repository.repository.Repository
import rx.Observable
import rx.Observer
import rx.Subscription

class SubscribedPodcastModelImpl(private val repository: Repository) : SubscribedPodcastModel {
    private var subscription: Subscription? = null
    private var observable: Observable<List<Podcast>>? = null

    override fun subscribeToSubscribedPodcasts(observer: Observer<List<Podcast>>) {
        if (observable == null) {
            observable = repository.getSubscribedPodcasts(observer)
        }
        subscription = observable?.subscribe(observer)
    }

    override fun unsubscribe() {
        subscription?.unsubscribe()
    }
}
