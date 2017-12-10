package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import models.pojos.Episode
import repo.repository.EpisodesRepository
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.Subscription


class FullscreenModelImpl(private val repo: EpisodesRepository, private val manithreadScheduler: Scheduler)
    : FullscreenModel {
    private var subscription: Subscription? = null
    override var episode: Episode? = null
    lateinit var observable: Observable<out Episode?>

    override fun getEpisodeByUriAsync(uri: String?) {
        uri ?: return
        observable = repo.getRealmManagedEpisodeByUriAsObservable(uri)
        subscribe(object : SimpleObserver<Episode>() {
            override fun onNext(feed: Episode?) {
                this@FullscreenModelImpl.episode = feed
            }
        })
    }

    override fun subscribe(observer: Observer<in Episode?>) {
        subscription = observable
                ?.observeOn(manithreadScheduler)
                ?.subscribe(observer)
    }

    override fun unSubscribe() {
        subscription?.unsubscribe()
    }
}

