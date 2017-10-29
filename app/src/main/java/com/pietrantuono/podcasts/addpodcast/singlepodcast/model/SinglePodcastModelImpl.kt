package com.pietrantuono.podcasts.addpodcast.singlepodcast.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers


class SinglePodcastModelImpl(
        private val singlePodcastApi: SinglePodcastApi,
        private val repository: PodcastRepo) : SinglePodcastModel {

    private var podcastFeedObservable: Observable<PodcastFeed>? = null
    private var podcast: Podcast? = null
    private var compositeSubscription: CompositeDisposable = CompositeDisposable()

    override fun startModel(podcast: Podcast?) {
        this.podcast = podcast
        if (podcast != null && podcast.feedUrl != null) {
            getFeed(podcast.feedUrl!!)
        }
    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {
        repository.subscribeUnsubscribeToPodcast(podcast)
    }

    override fun subscribeToFeed(observer: DisposableObserver<PodcastFeed>) {
        podcastFeedObservable?.let {
            compositeSubscription.add(it.subscribeWith(observer))
        }
    }

    override fun subscribeToIsSubscribedToPodcast(isSubscribedObserver: DisposableObserver<Boolean>) {
        compositeSubscription.add(repository.getIfSubscribed(podcast).subscribeWith(isSubscribedObserver))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }

    private fun getFeed(url: String) {
        podcastFeedObservable = singlePodcastApi.getFeed(url).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread()).cache()
        val subscription = podcastFeedObservable!!.subscribeWith(object : SimpleDisposableObserver<PodcastFeed>() {
            override fun onNext(feed: PodcastFeed) {
                podcast?.episodes = feed?.episodes
            }
        })
        compositeSubscription.add(subscription)
    }

}

