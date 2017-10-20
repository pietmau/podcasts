package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import rx.Observable
import rx.Observer
import rx.Subscription

class DownloadFragmentModelImpl(repo: PodcastRepo) : DownloadFragmentModel {
    private val observable: Observable<List<Podcast>> by lazy { repo.getSubscribedPodcasts() }
    private var subscription: Subscription? = null

    override fun unsubscribe() {
        subscription?.unsubscribe()
    }

    override fun subscribe(observer: Observer<List<Podcast>>) {
        subscription = observable.subscribe(observer)
    }

}