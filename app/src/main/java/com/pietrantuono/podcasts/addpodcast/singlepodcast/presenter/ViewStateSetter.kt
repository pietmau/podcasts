package com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter

import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import java.util.concurrent.TimeoutException

class ViewStateSetter(private val model: SinglePodcastModel) {
    private var view: SinglePodcastView? = null

    fun setView(view: SinglePodcastView?) {
        this.view = view
    }

    fun onGetFeed() {
        view?.setState(AddSinglePodcastActivity.State.LOADING)
    }

    fun onCompleted() {
        if (model.hasEpisodes) {
            view?.setState(AddSinglePodcastActivity.State.FULL)
            return
        }
        view?.setState(AddSinglePodcastActivity.State.EMPTY)

    }

    fun onError(throwable: Throwable?) {
        view?.setState(AddSinglePodcastActivity.State.ERROR)
        if (throwable is TimeoutException) {
            view?.onTimeout()
            return
        }
        view?.onError(throwable?.localizedMessage)
    }

}