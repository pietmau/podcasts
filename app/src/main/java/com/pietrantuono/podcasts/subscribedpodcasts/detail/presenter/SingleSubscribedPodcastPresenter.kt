package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter


import android.arch.lifecycle.ViewModel
import android.view.Menu
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView

class SingleSubscribedPodcastPresenter(
        private val model: SingleSubscribedModel,
        private val menuProvider: SingleSubscribedPodcastMenuProvider) : ViewModel() {

    var view: SingleSubscribedPodcastView? = null

    private var startedWithTransition: Boolean = false

    fun onStart(view: SingleSubscribedPodcastView, trackId: Int, startedWithTransition: Boolean) {
        this.view = view
        startPresenter(startedWithTransition)
        model.subscribe(trackId, object : SimpleObserver<Podcast>() {
            override fun onNext(feed: Podcast?) {
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

    fun onCreateOptionsMenu(menu: Menu): Boolean {
        return menuProvider.onCreateOptionsMenu(menu)
    }

}
