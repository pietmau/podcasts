package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import rx.Observable
import rx.Observer
import rx.Scheduler
import rx.subscriptions.CompositeSubscription

class DownloadFragmentModelImpl(
        private val podcastRepo: PodcastRepo,
        private val episodesRepository: EpisodesRepository,
        private val resources: ResourcesProvider,
        val mainThreadScheduler: Scheduler) : DownloadFragmentModel {
    private val observable: Observable<List<Podcast>> by lazy { podcastRepo.getSubscribedPodcasts() }
    private var subscription: CompositeSubscription = CompositeSubscription()

    override fun unsubscribe() {
        subscription.unsubscribe()
    }

    // TODO this is ugly
    override fun subscribe(observer: Observer<List<DownloadedPodcast>?>) {
        subscription.add(observable.map { it?.map { toDownloadedPodcast(it) } }.subscribe(observer))
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