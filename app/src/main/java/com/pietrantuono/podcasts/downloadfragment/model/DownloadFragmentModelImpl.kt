package com.pietrantuono.podcasts.downloadfragment.model
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.Episode
import models.pojos.Podcast
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

    private val observable: Observable<List<Podcast>> by lazy { podcastRepo.getSubscribedPodcasts() }

    override fun subscribe(observer: Observer<List<DownloadedPodcast>?>) {
        val subscription =
                observable
                        .flatMap { list ->
                            Observable.from(list)
                                    .map { toDownloadedPodcast(it) }
                                    .toList()
                        }
                        .subscribe(observer)
        compositeSubscription.add(subscription)
    }

    private fun toDownloadedPodcast(podcast: Podcast): DownloadedPodcast = DownloadedPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

    private fun makeEpisodes(podcast: Podcast): List<DownloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

    fun toDownloadedEpisode(episode: Episode): DownloadedEpisode = DownloadedEpisode(episode, resources)


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