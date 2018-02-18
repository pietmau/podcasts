package com.pietrantuono.podcasts.addpodcast.singlepodcast.model


import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.apis.SinglePodcastApi
import com.pietrantuono.podcasts.singlepodcast.presenter.SimpleObserver
import models.pojos.Podcast
import repository.PodcastRepo
import rx.Observer
import rx.Scheduler
import rx.subscriptions.CompositeSubscription
import java.util.concurrent.TimeUnit

class SinglePodcastModelImpl(
        private val singlePodcastApi: SinglePodcastApi,
        private val repository: PodcastRepo,
        private val ioScheduler: Scheduler,
        private val mainThreadScheduler: Scheduler) : SinglePodcastModel {

    override val hasEpisodes: Boolean
        get() = podcastFeed?.episodes?.isEmpty() == false

    override var alreadyAttemptedToGetFeed: Boolean = false
    override var podcastFeed: PodcastFeed? = null
    private var podcast: Podcast? = null
    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun startModel(podcast: Podcast?) {
        this.podcast = podcast
        if (podcast?.feedUrl != null) {
            getFeed(podcast.feedUrl!!)
        }
    }

    override fun onSubscribeUnsubscribeToPodcastClicked() {
        repository.subscribeUnsubscribeToPodcast(podcast)
    }

    override fun subscribeToFeed(observer: Observer<PodcastFeed>, timeOutTime: Long) {
        compositeSubscription.add(singlePodcastApi.getFeed(podcast?.feedUrl)
                .subscribeOn(ioScheduler)
                .timeout(timeOutTime, TimeUnit.SECONDS)
                .observeOn(mainThreadScheduler)
                .cache()
                .subscribe(observer))
    }

    override fun subscribeToIsSubscribedToPodcast(isSubscribedObserver: Observer<Boolean>) {
        compositeSubscription.add(repository.getIfSubscribed(podcast).subscribe(isSubscribedObserver))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }

    private fun getFeed(url: String) {
        compositeSubscription.add(singlePodcastApi.getFeed(url)
                .subscribeOn(ioScheduler)
                .cache()
                .subscribe(object : SimpleObserver<PodcastFeed>() {
                    override fun onNext(feed: PodcastFeed?) {
                        podcast?.episodes = feed?.episodes
                    }
                }))
    }

    override fun saveFeed(podcastFeed: PodcastFeed?) {
        podcast?.let { podcast ->
            podcastFeed?.episodes?.let { episodes ->
                repository.savePodcastAndEpisodesAsync(podcast, episodes)
            }
        }
    }
}
