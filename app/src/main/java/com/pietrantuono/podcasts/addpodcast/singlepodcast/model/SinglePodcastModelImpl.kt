package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import rx.Observable
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class SinglePodcastModelImpl(private val singlePodcastApi: SinglePodcastApi, private val repository: Repository) : SinglePodcastModel {
    private var podcastFeedObservable: Observable<PodcastFeed>? = null
    private var podcast: SinglePodcast? = null
    private var feed: Subscription? = null
    private var isSubscribedToPodcast: Subscription? = null

    override fun startModel(podcast: SinglePodcast?) {
        this.podcast = podcast
        if (podcast != null) {
            getFeed(podcast.feedUrl)
        }
    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {
        repository.onSubscribeUnsubscribeToPodcastClicked(podcast)
    }

    override fun subscribeToFeed(observer: Observer<PodcastFeed>) {
        feed = podcastFeedObservable!!.subscribeOn(Schedulers.newThread())
                .cache()
                .observeOn(AndroidSchedulers.mainThread()).subscribe(observer)
    }

    override fun subscribeToIsSubscribedToPodcast(isSubscribedObserver: Observer<Boolean>) {
        isSubscribedToPodcast = getIsSubscribedToPodcast().subscribe(isSubscribedObserver)
    }

    override fun unsubscribe() {
        if (feed != null) {
            feed!!.unsubscribe()
        }
        if (isSubscribedToPodcast != null) {
            isSubscribedToPodcast!!.unsubscribe()
        }
    }

    private fun getFeed(url: String) {
        podcastFeedObservable = singlePodcastApi.getFeed(url)
    }

    private fun getIsSubscribedToPodcast(): Observable<Boolean> {
        return repository.getIfSubscribed(podcast)
    }
}