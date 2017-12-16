package com.pietrantuono.podcasts.downloadfragment.presenter

import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadAdapter
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast

class DownloadFragmentPresenter(
        private val model: DownloadFragmentModel,
        private val messageCreator: MessageCreator,
        private val downloader: Downloader,
        private val dataChecker: DataChecker,
        private val logger: DebugLogger
) : DownloadAdapter.Callback {
    private var view: DownloadView? = null
    private val TAG: String? = "DownloadFragmentPresenter"

    fun bindView(view: DownloadView) {
        this.view = view
        this.dataChecker.presenter = this
    }

    fun bind() {
        model.subscribe(object : SimpleObserver<List<DownloadedPodcast>>() {
            override fun onNext(feed: List<DownloadedPodcast>) {
                dataChecker.onDataReceived(feed)
            }
        })
    }

    fun onNewData(feed: List<DownloadedPodcast>) {
        view?.setPodcasts(feed)
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
        view?.confirmDownloadAllEpisodes(message, downloadedPodcast)
    }

    override fun deleteEpisode(downloadedEpisode: DownloadedEpisode?) {
        if (downloadedEpisode?.title == null || downloadedEpisode?.link == null) {
            return
        }
        val message = messageCreator.confirmDeleteEpisode(downloadedEpisode.title)
        view?.confirmDeleteEpisode(message, downloadedEpisode)
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

    fun onConfirmDownloadAllEpisodes(podcast: DownloadedPodcast?) {
        val time = System.currentTimeMillis()
        logger.error(TAG, "onConfirmDownloadAllEpisodes START")
        downloader.downloadPodcast(podcast)
        logger.error(TAG, "onConfirmDownloadAllEpisodes END  " + (System.currentTimeMillis() - time))
    }

    fun onConfirmDeleteEpisode(episode: DownloadedEpisode?) {
        downloader.deleteEpisode(episode)
    }

    fun onConfirmDeleteAllEpisodes(podcast: DownloadedPodcast?) {
        downloader.deleteAllEpisodes(podcast)
    }

    fun updateEpisode(i: Int, j: Int, downloadedEpisode: DownloadedEpisode) {
        view?.updateItem(i, j, downloadedEpisode)
    }

    fun updatePodcast(i: Int, newPodcast: DownloadedPodcast) {
        view?.updatePodcast(i, newPodcast)
    }

}

