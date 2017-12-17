package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.fabric.sdk.android.services.concurrency.AsyncTask
import io.realm.Realm
import models.pojos.Episode
import models.pojos.Podcast
import models.pojos.PodcastRealm
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo
import rx.Observer
import rx.Scheduler
import rx.subjects.PublishSubject
import rx.subscriptions.CompositeSubscription

class DownloadFragmentModelImpl(
        private val podcastRepo: PodcastRepo,
        private val episodesRepository: EpisodesRepository,
        private val resources: ResourcesProvider,
        private val mainThreadScheduler: Scheduler,
        private val workerScheduler: Scheduler,
        private val logger: DebugLogger) : DownloadFragmentModel {

    private val TAG = "DownloadFragmentModelImpl"

    private val compositeSubscription: CompositeSubscription = CompositeSubscription()

    private val subject = PublishSubject.create<List<DownloadedPodcast>>()

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

    override fun subscribe(observer: Observer<List<DownloadedPodcast>>) {
        diocan(observer)
    }

    private fun diocan(observer: Observer<List<DownloadedPodcast>>) {
        subject.subscribe(observer)
        Realm.getDefaultInstance()
                .where(PodcastRealm::class.java)
                .equalTo("podcastSubscribed", true)
                .findAllAsync()
                .asObservable()
                .subscribe { DiocanTasx(resources, subject).execute() }
    }

    class DiocanTasx(
            private val resources: ResourcesProvider,
            private val subject: PublishSubject<List<DownloadedPodcast>>)
        : AsyncTask<Void, List<DownloadedPodcast>, List<DownloadedPodcast>>() {

        override fun doInBackground(vararg p0: Void?): List<DownloadedPodcast> {
            val result = Realm.getDefaultInstance().where(PodcastRealm::class.java)
                    .equalTo("podcastSubscribed", true)
                    .findAll()
                    .toList()
                    .map { toDownloaded(it as Podcast) }
            return result
        }

        override fun onPostExecute(result: List<DownloadedPodcast>?) {
            subject.onNext(result)
        }

        fun toDownloaded(podcast: Podcast): DownloadedPodcast = DownloadedPodcast.fromPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

        private fun makeEpisodes(podcast: Podcast): List<DownloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

        fun toDownloadedEpisode(episode: Episode): DownloadedEpisode = DownloadedEpisode.fromEpisode(episode, resources)
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