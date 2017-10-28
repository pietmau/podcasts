package com.pietrantuono.podcasts.downloadfragment.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DowloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import rx.Observable
import rx.Observer
import rx.Subscription

class DownloadFragmentModelImpl(
        repo: PodcastRepo,
        private val resources: ResourcesProvider) : DownloadFragmentModel {
    private val observable: Observable<List<Podcast>> by lazy { repo.getSubscribedPodcasts() }
    private var subscription: Subscription? = null

    override fun unsubscribe() {
        subscription?.unsubscribe()
    }

    override fun subscribe(observer: Observer<List<DownloadedPodcast>?>) {
        subscription = observable.map { it?.map { toDownloadedPodcast(it) } }.subscribe(observer)
    }

    private fun toDownloadedPodcast(podcast: Podcast): DownloadedPodcast = DownloadedPodcast(podcast, podcast.trackName, makeEpisodes(podcast), resources)

    private fun makeEpisodes(podcast: Podcast): List<DowloadedEpisode>? = podcast.episodes?.map { toDownloadedEpisode(it) }

    fun toDownloadedEpisode(episode: Episode): DowloadedEpisode = DowloadedEpisode(episode, resources)


}