package com.pietrantuono.podcasts.downloadfragment.presenter


import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SimpleDisposableObserver
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
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
        model.subscribe(object : SimpleDisposableObserver<List<DownloadedPodcast>>() {
            override fun onNext(feed: List<DownloadedPodcast>) {
                if (feed != null && !feed.isEmpty()) {
                    view?.setPodcasts(feed)
                }
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

    override fun downloadEpisodes(downloadedPodcast: DownloadedPodcast?) {
        downloadedPodcast?.trackId?.let {
            model.getPodcastTitleAsync(object : SimpleObserver<String?>() {
                override fun onNext(title: String) {
                    //title?.let { view?.confirmDownloadEpisode(it) }
                }
            }, it)
        }
    }

    override fun deleteEpisode(link: DownloadedEpisode?) {
        //episode?.let { view?.confirmDeleteEpisode(it.title) }
    }

    override fun deleteEpisodes(trackId: DownloadedPodcast?) {
        //podcast?.let { view?.confirmDeleteEpisodes(it.title) }
    }

    fun onConfirmDownloadEpisode(link: String) {
        downloader.downloadEpisodeFromLink(link)
    }
}

