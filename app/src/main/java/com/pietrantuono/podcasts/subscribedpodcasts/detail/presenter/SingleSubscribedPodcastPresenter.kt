package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter


import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView

class SingleSubscribedPodcastPresenter(private val model: SingleSubscribedModel) : ViewModel() {

    var view: SingleSubscribedPodcastView? = null

    private var startedWithTransition: Boolean = false

    fun onStart(view: SingleSubscribedPodcastView, trackId: Int, startedWithTransition: Boolean) {
        this.view = view
        startPresenter(startedWithTransition)
        model.subscribe(trackId, object : SimpleObserver<SinglePodcast>() {
            override fun onNext(feed: SinglePodcast?) {
                if (view != null && feed != null && feed.episodes != null) {
                    view.setEpisodes(feed.episodes)
                    view?.setTitle(feed?.collectionName)
                }
            }
        })
    }

    fun onStop() {
        this.view = null
    }

    private fun startPresenter(startedWithTransition: Boolean) {
        this.startedWithTransition = startedWithTransition
        if (startedWithTransition) {
            view?.enterWithTransition()
        } else {
            view?.enterWithoutTransition()
        }
    }


}
