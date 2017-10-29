package com.pietrantuono.podcasts.fullscreenplay.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleDisposableObserver
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableObserver
import java.util.concurrent.TimeUnit


class FullscreenModelImpl(private val repo: EpisodesRepository, private val manithreadScheduler: Scheduler)
    : FullscreenModel {
    private var cached: Observable<out Episode>? = null
    override var episode: Episode? = null
    private var subscription: DisposableObserver<in Episode>? = null

    override fun getEpisodeByUrlAsync(url: String?) {
        url ?: return
        cached = repo
                .getEpisodeByUrlAsObservable(url)
                .cache()
        subscribe(object : SimpleDisposableObserver<Episode>() {
            override fun onNext(feed: Episode) {
                this@FullscreenModelImpl.episode = feed
            }
        })
    }

    override fun subscribe(observer: DisposableObserver<in Episode>) {
        subscription = cached
                ?.take(1)
                ?.timeout(1, TimeUnit.SECONDS)
                ?.observeOn(manithreadScheduler)
                ?.subscribeWith(observer)
    }

    override fun unSubscribe() {
        subscription?.dispose()
    }
}

