package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import models.pojos.Episode
import repo.repository.EpisodesRepository
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.Subscription
import java.util.concurrent.TimeUnit


class FullscreenModelImpl(private val repo: EpisodesRepository, private val manithreadScheduler: Scheduler)
    : FullscreenModel {
    private var cached: Observable<out Episode>? = null
    private var subscription: Subscription? = null
    override var episode: Episode? = null

    override fun getEpisodeByUriAsync(uri: String?) {
        uri ?: return
        cached = repo
                .getEpisodeByUriAsObservable(uri)
                .cache()
        subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(feed: Episode?) {
                this@FullscreenModelImpl.episode = feed
            }
        })
    }

    override fun subscribe(observer: Observer<in Episode>) {
        subscription = cached
                ?.take(1)
                ?.timeout(1, TimeUnit.SECONDS)
                ?.observeOn(manithreadScheduler)
                ?.subscribe(observer)
    }

    override fun unSubscribe() {
        subscription?.unsubscribe()
    }
}

