package com.pietrantuono.podcasts.subscribedpodcasts.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import io.reactivex.Observable
import io.reactivex.observers.DisposableObserver

class SubscribedPodcastModelImpl(private val repository: PodcastRepo) : SubscribedPodcastModel {
    private var observable: Observable<List<Podcast>>? = null
    private var subscription: DisposableObserver<List<Podcast>>? = null

    override fun subscribeToSubscribedPodcasts(observer: DisposableObserver<List<Podcast>>) {
        if (observable == null) {
            observable = repository.getSubscribedPodcasts()
        }
        subscription = observable?.subscribeWith(observer)
    }

    override fun unsubscribe() {
        subscription?.dispose()
    }
}
