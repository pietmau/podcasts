package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.realm.Realm
import models.pojos.PodcastRealm
import repository.PodcastRepo
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
    private val realm = Realm.getDefaultInstance()
    private var subject: PublishSubject<List<DownloadedPodcast>>? = null
    private var getDownloadsTask: GetDownloadsTask? = null

    override fun unsubscribe() {
        subject = null
        compositeSubscription.unsubscribe()
        getDownloadsTask?.removeCallback()
    }


    override fun subscribe(observer: Observer<List<DownloadedPodcast>>) {
        subject = PublishSubject.create<List<DownloadedPodcast>>()
        compositeSubscription.add(subject?.subscribe(observer))
        realm.where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .subscribe {
                    getDownloadsTask = GetDownloadsTask(resources, subject)
                    getDownloadsTask?.execute()
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