package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.singlepodcast.presenter.SimpleObserver
import models.pojos.Episode
import repo.repository.EpisodesRepository
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class FullscreenModelImpl(private val repo: EpisodesRepository, private val manithreadScheduler: Scheduler)
    : FullscreenModel {
    private var compositeSubscription: CompositeSubscription? = null
    override var episode: Episode? = null
    lateinit var observable: Observable<out Episode?>

    override fun getEpisodeByUriAsync(uri: String?) {
        compositeSubscription = CompositeSubscription()
        uri ?: return
        observable = repo.getEpisodeByUriAsObservable(uri)
        subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(feed: Episode?) {
                this@FullscreenModelImpl.episode = feed
            }
        })
    }

    override fun subscribe(observer: Observer<in Episode?>) {
        val subscription = observable.observeOn(manithreadScheduler)
                ?.subscribe(observer)
        compositeSubscription?.add(subscription)
    }

    override fun unSubscribe() {
        compositeSubscription?.unsubscribe()
    }
}

