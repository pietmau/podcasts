package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter


import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView
import rx.Observer

class SingleSubscribedPodcastPresenter(private val model: SingleSubscribedModel) : ViewModel() {

    var view: SingleSubscribedPodcastView? = null

    private var startedWithTransition: Boolean = false

    fun onStart(view: SingleSubscribedPodcastView, trackId: Int, startedWithTransition: Boolean) {
        this.view = view
        startPresenter(startedWithTransition)
        model.subscribe(trackId, object : Observer<SinglePodcastRealm> {
            override fun onError(e: Throwable?) {}

            override fun onCompleted() {}

            override fun onNext(feed: SinglePodcastRealm?) {
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
