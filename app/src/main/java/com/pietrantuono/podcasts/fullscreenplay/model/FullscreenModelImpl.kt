package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.Subscription
import java.util.concurrent.TimeUnit


class FullscreenModelImpl(private val repo: EpisodesRepository, private val manithreadScheduler: Scheduler)
    : FullscreenModel {
    private var cached: Observable<out Episode>? = null
    private var subscription: Subscription? = null

    override fun getEpisodeByUrl(url: String?) {
        cached = repo
                .getEpisodeByUrlAsObservable(url)
                .cache()
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

