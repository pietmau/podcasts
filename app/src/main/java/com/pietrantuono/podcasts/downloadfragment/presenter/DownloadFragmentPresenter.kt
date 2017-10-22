package com.pietrantuono.podcasts.downloadfragment.presenter


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.downloadfragment.model.DownloadFragmentModel
import com.pietrantuono.podcasts.downloadfragment.view.DownloadView


class DownloadFragmentPresenter(private val model: DownloadFragmentModel) {
    private var view: DownloadView? = null

    fun bindView(view: DownloadView) {
        this.view = view
    }

    fun bind() {
        model.subscribe(object : SimpleObserver<List<Podcast>>() {
            override fun onNext(feed: List<Podcast>?) {
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

