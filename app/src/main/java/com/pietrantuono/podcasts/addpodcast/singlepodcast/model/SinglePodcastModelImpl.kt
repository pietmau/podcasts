package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import rx.Observable
import rx.Observer
import rx.subscriptions.CompositeSubscription

class SinglePodcastModelImpl(private val singlePodcastApi: SinglePodcastApi, private val repository:
Repository) : SinglePodcastModel {
    private var podcastFeedObservable: Observable<PodcastFeed>? = null
    private var podcast: SinglePodcast? = null
    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun startModel(podcast: SinglePodcast?) {
        this.podcast = podcast
        podcast?.let {
            getFeed(podcast.feedUrl)
        }
    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {
        repository.onSubscribeUnsubscribeToPodcastClicked(podcast)
    }

    override fun subscribeToFeed(observer: Observer<PodcastFeed>) {
        compositeSubscription.add(podcastFeedObservable?.subscribe(observer))
    }

    override fun subscribeToIsSubscribedToPodcast(isSubscribedObserver: Observer<Boolean>) {
        compositeSubscription.add(getIsSubscribedToPodcast().subscribe(isSubscribedObserver))
    }

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

    private fun getFeed(url: String) {
        podcastFeedObservable = singlePodcastApi.getFeed(url).cache().share().replay()
        val subscription = podcastFeedObservable!!.subscribe(object : Observer<PodcastFeed> {
            override fun onError(e: Throwable?) {}

            override fun onNext(t: PodcastFeed?) {
            }

            override fun onCompleted() {}
        })
        compositeSubscription.add(subscription)
    }

    private fun getIsSubscribedToPodcast(): Observable<Boolean> {
        return repository.getIfSubscribed(podcast)
    }
}
