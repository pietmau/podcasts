package com.pietrantuono.podcasts.subscribedpodcasts.list.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import pojos.Podcast
import repo.repository.PodcastRepo
import rx.Observer
import rx.subscriptions.CompositeSubscription

class EpisodesListModelImpl(private val repository: PodcastRepo, private val downLoadManager:
Downloader) : EpisodesListModel() {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()
    private var feed: Podcast? = null

    override fun onDownLoadAllSelected() {
        if (feed != null && feed!!.episodes != null) {
            downLoadManager.downLoadAllEpisodes(feed!!.episodes!!)
        }
    }

    override fun unsubscribeFromPodcast() {
        repository.subscribeUnsubscribeToPodcast(feed)
    }

    override fun subscribe(trackId: Int, observer: Observer<in Podcast>) {
        val observable = repository.getPodcastByIdAsync(trackId)
        compositeSubscription.add(observable.subscribe(observer))
        compositeSubscription.add(observable.subscribe(object : SimpleObserver<Podcast>() {
            override fun onNext(feed: Podcast?) {
                this@EpisodesListModelImpl.feed = feed
            }
        }))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }
}



