package com.pietrantuono.podcasts.subscribedpodcasts.list.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleDisposableObserver
import com.pietrantuono.podcasts.downloader.PodcastDownLoadManager
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver

class EpisodesListModelImpl(private val repository: PodcastRepo, private val downLoadManager:
PodcastDownLoadManager) : EpisodesListModel() {
    private val compositeSubscription: CompositeDisposable = CompositeDisposable()
    private var feed: Podcast? = null

    override fun onDownLoadAllSelected() {
        if (feed != null && feed!!.episodes != null) {
            downLoadManager.downLoadAll(feed!!.episodes!!)
        }
    }

    override fun unsubscribeFromPodcast() {
        repository.subscribeUnsubscribeToPodcast(feed)
    }

    override fun subscribe(trackId: Int, observer: DisposableObserver<in Podcast>) {
        val observable = repository.getPodcastByIdAsync(trackId)
        compositeSubscription.add(observable.subscribeWith(observer))
        compositeSubscription.add(observable.subscribeWith(object : SimpleDisposableObserver<Podcast>() {
            override fun onNext(feed: Podcast) {
                this@EpisodesListModelImpl.feed = feed
            }
        }))
    }

    override fun unsubscribe() {
        compositeSubscription.clear()
    }
}



