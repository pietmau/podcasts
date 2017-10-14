package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.repository.repository.Repository
import rx.Observable
import rx.Observer
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

class SinglePodcastModelImpl(
        private val singlePodcastApi: SinglePodcastApi,
        private val repository: Repository) : SinglePodcastModel {

    private var podcastFeedObservable: Observable<PodcastFeed>? = null
    private var podcast: Podcast? = null
    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun startModel(podcast: Podcast?) {
        this.podcast = podcast
        if (podcast != null && podcast.feedUrl != null) {
            getFeed(podcast.feedUrl!!)
        }
    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {
        repository.subscribeUnsubscribeToPodcast(podcast)
    }

    override fun subscribeToFeed(observer: Observer<PodcastFeed>) {
        podcastFeedObservable?.let {
            compositeSubscription.add(it.subscribe(observer))
        }
    }

    override fun subscribeToIsSubscribedToPodcast(isSubscribedObserver: Observer<Boolean>) {
        compositeSubscription.add(repository.getIfSubscribed(podcast).subscribe(isSubscribedObserver))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }

    private fun getFeed(url: String) {
        podcastFeedObservable = singlePodcastApi.getFeed(url).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache()
        val subscription = podcastFeedObservable!!.subscribe(object : SimpleObserver<PodcastFeed>() {
            override fun onNext(feed: PodcastFeed?) {
                podcast?.episodes = feed?.episodes
            }
        })
        compositeSubscription.add(subscription)
    }

}
