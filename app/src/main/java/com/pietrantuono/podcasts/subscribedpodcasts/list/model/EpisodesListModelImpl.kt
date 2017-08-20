package com.pietrantuono.podcasts.subscribedpodcasts.list.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.downloader.PodcastDownLoadManager
import com.pietrantuono.podcasts.repository.repository.Repository
import rx.Observer
import rx.subscriptions.CompositeSubscription

class EpisodesListModelImpl(private val repository: Repository, private val downLoadManager:
PodcastDownLoadManager) : EpisodesListModel() {
    private val compositeSubscription: CompositeSubscription = CompositeSubscription()
    private var feed: Podcast? = null

    override fun onDownLoadAllSelected() {
        if (feed != null && feed!!.episodes != null) {
            downLoadManager.downLoadAll(feed!!.episodes!!)
        }
    }

    override fun unsubscribeFromPodcast() {
        repository.subscribeUnsubscribeToPodcast(feed)
    }

    override fun subscribe(trackId: Int, observer: Observer<in Podcast>) {
        val observable = repository.getPodcastById(trackId)
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



