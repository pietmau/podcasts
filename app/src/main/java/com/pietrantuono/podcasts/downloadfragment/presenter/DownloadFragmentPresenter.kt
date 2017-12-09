package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadAdapter
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast
import models.pojos.Episode
import models.pojos.Podcast


class DownloadFragmentPresenter(
        private val model: DownloadFragmentModel,
        private val messageCreator: MessageCreator,
        private val downloader: Downloader
) : DownloadAdapter.Callback {
    private var view: DownloadView? = null
    private var feed: MutableList<DownloadedPodcast> = mutableListOf()

    fun bindView(view: DownloadView) {
        this.view = view
    }

    fun bind() {
        model.subscribe(object : SimpleObserver<List<DownloadedPodcast>>() {
            override fun onNext(feed: List<DownloadedPodcast>) {
                if (feed != null && !feed.isEmpty()) {
                    onDataReceived(feed)
                }
            }
        })
    }

    private fun onDataReceived(feed: List<DownloadedPodcast>) {
        if (this.feed == null || this.feed?.isEmpty() == true) {
            onNewData(feed)
            return
        }
        onDataUpdate(feed)
    }

    fun onDataUpdate(feed: List<DownloadedPodcast>?) {
        val copy = feed
        if (copy == null || copy?.size != this.feed.size) {
            return
        }
        iterateOverFeeds(copy)
    }

    private fun iterateOverFeeds(copy: List<DownloadedPodcast>) {
        for (i in feed.indices) {
            val feedEpisodes = feed[i].items
            val copyEpisodes = copy[i].items
            if (copyEpisodes == null || feedEpisodes == null || copyEpisodes.size != feedEpisodes.size) {
                return
            }
            iterateOverEpisodes(feedEpisodes, i, copyEpisodes)
        }
    }

    private fun iterateOverEpisodes(feedEpisodes: MutableList<DownloadedEpisode>, i: Int, copyEpisodes: MutableList<DownloadedEpisode>) {
        for (j in feedEpisodes.indices) {
            if (feedEpisodes[j].downloaded != copyEpisodes[j].downloaded) {
                view?.updateItem(i, j, copyEpisodes[j])
                feed?.get(i)?.items?.set(j, copyEpisodes[j])
            }
        }
    }

    private fun onNewData(feed: List<DownloadedPodcast>) {
        val copy = feed
        this.feed.clear()
        this.feed.addAll(copy)
        view?.setPodcasts(copy)
    }

    private fun makeCopy(feed: List<DownloadedPodcast?>?): List<DownloadedPodcast?>? {
//        val realm = Realm.getDefaultInstance()
//        val copy = feed
//                .map { downloadedPodcast ->
//                    val podcast = realm.copyFromRealm(downloadedPodcast.podcast as PodcastRealm)
//                    DownloadedPodcast(podcast, downloadedPodcast.title, downloadedPodcast.items, downloadedPodcast.resources)
//                }
        return feed
    }

    fun unbind() {
        model.unsubscribe()
    }

    override fun downloadEpisode(downloadedEpisode: DownloadedEpisode?) {
        downloadedEpisode?.uri ?: return
        downloadedEpisode?.title ?: return
        val message = messageCreator.confirmDownloadEpisode(downloadedEpisode.title)
        view?.confirmDownloadEpisode(message, downloadedEpisode.uri)
    }

    override fun downloadAllEpisodes(downloadedPodcast: DownloadedPodcast?) {
        if (downloadedPodcast?.title == null || downloadedPodcast?.trackId == null) {
            return
        }
        val message = messageCreator.confirmDownloadAllEpisodes(downloadedPodcast.title)
        view?.confirmDownloadAllEpisodes(message, downloadedPodcast.items)
    }

    override fun deleteEpisode(downloadedEpisode: DownloadedEpisode?) {
        if (downloadedEpisode?.title == null || downloadedEpisode?.link == null) {
            return
        }
        val message = messageCreator.confirmDeleteEpisode(downloadedEpisode.title)
        view?.confirmDeleteEpisode(message, downloadedEpisode.episode)
    }

    override fun deleteAllEpisodes(podcast: DownloadedPodcast?) {
        if (podcast?.title == null || podcast?.trackId == null) {
            return
        }
        val message = messageCreator.confirmDeleteAllEpisode(podcast.title)
        view?.confirmDeleteAllEpisodes(message, podcast)
    }

    fun onConfirmDownloadEpisode(uri: String) {
        downloader.downloadEpisodeFromUri(uri)
    }

    fun onConfirmDownloadAllEpisodes(podcast: Podcast) {
        downloader.downloadIfAppropriate(podcast)
    }

    fun onConfirmDeleteEpisode(episode: Episode) {
        downloader.deleteEpisode(episode)
    }

    fun onConfirmDeleteAllEpisodes(podcast: Podcast) {
        downloader.deleteAllEpisodes(podcast)
    }
}

