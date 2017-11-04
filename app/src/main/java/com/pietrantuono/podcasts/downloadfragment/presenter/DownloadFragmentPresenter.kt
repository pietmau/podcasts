package com.pietrantuono.podcasts.downloadfragment.presenter


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadAdapter
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


class DownloadFragmentPresenter(
        private val model: DownloadFragmentModel,
        private val messageCreator: MessageCreator,
        private val downloader: Downloader
) : DownloadAdapter.Callback {
    private var view: DownloadView? = null

    fun bindView(view: DownloadView) {
        this.view = view
    }

    fun bind() {
        model.subscribe(object : SimpleObserver<List<DownloadedPodcast>>() {
            override fun onNext(feed: List<DownloadedPodcast>?) {
                if (feed != null && !feed.isEmpty()) {
                    view?.setPodcasts(feed)
                }
                model.unsubscribe()
            }
        })
    }

    fun unbind() {
        model.unsubscribe()
    }

    override fun downloadEpisode(downloadedEpisode: DownloadedEpisode?) {
        if (downloadedEpisode?.title == null || downloadedEpisode?.link == null) {
            return
        }
        val message = messageCreator.confirmDownloadEpisode(downloadedEpisode.title)
        view?.confirmDownloadEpisode(message, downloadedEpisode.link)
    }

    override fun downloadAllEpisodes(downloadedPodcast: DownloadedPodcast?) {
        if (downloadedPodcast?.title == null || downloadedPodcast?.trackId == null) {
            return
        }
        val message = messageCreator.confirmDownloadAllEpisodes(downloadedPodcast.title)
        view?.confirmDownloadAllEpisodes(message, downloadedPodcast.podcast)
    }

    override fun deleteEpisode(downloadedEpisode: DownloadedEpisode?) {
        if (downloadedEpisode?.title == null || downloadedEpisode?.link == null) {
            return
        }
        val message = messageCreator.confirmDeleteEpisode(downloadedEpisode.title)
        view?.confirmDeleteEpisode(message, downloadedEpisode.episode)
    }

    override fun deleteAllEpisodes(trackId: DownloadedPodcast?) {
        //podcast?.let { view?.confirmDeleteAllEpisodes(it.title) }
    }

    fun onConfirmDownloadEpisode(link: String) {
        downloader.downloadEpisodeFromLink(link)
    }

    fun onConfirmDownloadAllEpisodes(podcast: Podcast) {
        downloader.downloadIfAppropriate(podcast)
    }

    fun onConfirmDeleteEpisode(episode: Episode) {
        downloader.deleteEpisode(episode)
    }
}

