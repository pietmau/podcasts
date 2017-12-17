package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.realm.Realm
import models.pojos.PodcastRealm
import repo.repository.PodcastRepo
import rx.Observer
import rx.Scheduler
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

class DownloadFragmentModelImpl(
        private val podcastRepo: PodcastRepo,
        private val resources: ResourcesProvider,
        private val mainThreadScheduler: Scheduler,
        private val logger: DebugLogger) : DownloadFragmentModel {

    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    private var subject: PublishSubject<List<DownloadedPodcast>>? = null

    override fun unsubscribe() {
        subject = null
        compositeSubscription.unsubscribe()
    }

    override fun subscribe(observer: Observer<List<DownloadedPodcast>>) {
        subject = PublishSubject.create<List<DownloadedPodcast>>()
        compositeSubscription.add(subject?.subscribe(observer))
        Realm.getDefaultInstance().use { realm->
            realm.where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .subscribe { GetDownloadsTask(resources, subject).execute() }
        }
    }

    override fun getPodcastTitleAsync(observer: Observer<String?>, trackId: Int?) {
        trackId?.let {
            podcastRepo.getPodcastByIdAsync(it)
                    .map { episode -> episode?.trackName }
                    .filter { it != null }
                    .observeOn(mainThreadScheduler)
                    .subscribe(observer)
        }
    }
}