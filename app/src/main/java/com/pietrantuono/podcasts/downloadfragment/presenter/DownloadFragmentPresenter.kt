package com.pietrantuono.podcasts.downloadfragment.presenter


import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadAdapter
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedEpisode
import com.pietrantuono.podcasts.downloadfragment.view.custom.DownloadedPodcast


class DownloadFragmentPresenter(private val model: DownloadFragmentModel) : DownloadAdapter.Callback {

    override fun downloadEpisode(episode: DownloadedEpisode?) {
        view?.confirmDownloadEpisode(episode)
    }

    override fun downloadEpisodes(episodes: List<DownloadedEpisode>?) {
        view?.confirmDownloadEpisodes(episodes)
    }

    override fun deleteEpisode(episode: DownloadedEpisode?) {
        view?.confirmDeleteEpisode(episode)
    }

    override fun deleteEpisodes(episodes: List<DownloadedEpisode>?) {
        view?.confirmDeleteEpisodes(episodes)
    }

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
            }
        })
    }

    fun unbind() {
        model.unsubscribe()
    }
}

