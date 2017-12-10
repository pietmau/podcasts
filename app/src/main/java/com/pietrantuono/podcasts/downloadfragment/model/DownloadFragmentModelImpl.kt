package com.pietrantuono.podcasts.downloadfragment.model

import android.util.Log
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import io.realm.Realm
import models.pojos.Episode
import models.pojos.Podcast
import models.pojos.PodcastRealm
import repo.repository.EpisodesRepository
import repo.repository.PodcastRepo
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class DownloadFragmentModelImpl(
        private val podcastRepo: PodcastRepo,
        private val episodesRepository: EpisodesRepository,
        private val resources: ResourcesProvider,
        private val mainThreadScheduler: Scheduler,
        private val workerScheduler: Scheduler,
        private val logger: DebugLogger) : DownloadFragmentModel {

    private val TAG = "DownloadFragmentModelImpl"

    private var compositeSubscription: CompositeSubscription = CompositeSubscription()

    override fun unsubscribe() {
        compositeSubscription.unsubscribe()
    }

    override fun subscribe(observer: Observer<List<DownloadedPodcast>>) {
        Log.d("DIOCAN ", "--------------ENTRA--------------" + System.currentTimeMillis())
        val subscription =
                podcastRepo
                        .getSubscribedPodcastsAsObservable()
                        .subscribeOn(mainThreadScheduler)
                        .observeOn(workerScheduler)
                        .map {
                            Realm.getDefaultInstance().use {
                                it.copyFromRealm(it.where(PodcastRealm::class.java).equalTo("podcastSubscribed", true).findAll())
                            }
                        }
                        .flatMap { list ->
                            Observable.from(list)
                                    .doOnNext { Log.d("DIOCAN ", "3 " + System.currentTimeMillis()) }
                                    .map { toDownloaded(it) }
                                    .doOnNext { Log.d("DIOCAN ", "4 " + System.currentTimeMillis()) }
                                    .toList()
                                    .doOnNext { Log.d("DIOCAN ", "5 " + System.currentTimeMillis()) }
                        }
                        .doOnNext { Log.d("DIOCAN ", "6 " + System.currentTimeMillis()) }
                        .doOnNext { Log.d("DIOCAN ", "----------end----------") }
                        .doOnNext { Log.d("DIOCAN ", "----------end----------") }
                        .observeOn(mainThreadScheduler)
                        .subscribe(observer)
        compositeSubscription.add(subscription)
        Log.d("DIOCAN ", "--------------ESCE--------------" + System.currentTimeMillis())
    }

    override fun getSubscedPodcasts() = podcastRepo.getSubscedPodcasts()

    fun toDownloadedEpisode(episode: Episode): DownloadedEpisode = DownloadedEpisode.fromEpisode(episode, resources)

    override fun getPodcastTitleAsync(observer: Observer<String?>, trackId: Int?) {
        trackId?.let {
            podcastRepo.getPodcastByIdAsync(it)
                    .map { episode -> episode?.trackName }
                    .filter { it != null }
                    .observeOn(mainThreadScheduler)
                    .subscribe(observer)
        }
    }

    fun toDownloaded(podcast: Podcast): DownloadedPodcast = DownloadedPodcast.fromPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

    private fun makeEpisodes(podcast: Podcast): List<DownloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

}