package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import com.pietrantuono.podcasts.providers.PodcastRealm
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import io.realm.Realm
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

    private val observable: Observable<List<Podcast>> by lazy { podcastRepo.getSubscribedPodcasts() }

    // TODO this is ugly
    override fun subscribe(observer: Observer<List<DownloadedPodcast>?>) {
        logger.debug(TAG, "prima " + Thread.currentThread().name+" "+System.currentTimeMillis())
        val subscription = Realm.getDefaultInstance().use { realm ->
            realm.where(PodcastRealm::class.java)
                    .equalTo("podcastSubscribed", true)
                    .findAllAsync()
                    .asObservable()
                    .doOnNext { logger.debug(TAG, "0 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    .subscribeOn(mainThreadScheduler)
                    .doOnNext { logger.debug(TAG, "1 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    //.filter { it.isLoaded && it.isValid }
                    //.map { realm.copyFromRealm(it) }
                    //.map { it as List<Podcast> }
                    .doOnNext { logger.debug(TAG, "2 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    .observeOn(workerScheduler)
                    .doOnNext { logger.debug(TAG, "3 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    .map {
                        Realm.getDefaultInstance().use {
                            it.copyFromRealm(it.where(PodcastRealm::class.java).equalTo("podcastSubscribed", true).findAll())
                        }
                    }
                    .map { it?.map { toDownloadedPodcast(it) } }
                    .doOnNext { logger.debug(TAG, "4 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    .observeOn(mainThreadScheduler)
                    .doOnNext { logger.debug(TAG, "5 " + Thread.currentThread().name+" "+System.currentTimeMillis()) }
                    .subscribe(observer)
            //compositeSubscription.add(subscription)
        }
        logger.debug(TAG, "dopo " + Thread.currentThread().name+" "+System.currentTimeMillis())
    }

    private fun toDownloadedPodcast(podcast: Podcast): DownloadedPodcast = DownloadedPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

    private fun makeEpisodes(podcast: Podcast): List<DownloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

    fun toDownloadedEpisode(episode: Episode): DownloadedEpisode = DownloadedEpisode(episode, resources)

    override fun getEpisodeTitleAsync(observer: Observer<String?>, link: String?) {
        link?.let {
            episodesRepository.getEpisodeByUrlAsync(it)
                    .map { episode -> episode?.title }
                    .filter { it != null }
                    .observeOn(mainThreadScheduler)
                    .subscribe(observer)
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