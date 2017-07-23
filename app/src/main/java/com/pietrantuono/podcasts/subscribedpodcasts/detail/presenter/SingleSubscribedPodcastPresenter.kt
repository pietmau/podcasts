package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter


import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import com.pietrantuono.podcasts.subscribedpodcasts.detail.views.SingleSubscribedPodcastView
import rx.Observer

class SingleSubscribedPodcastPresenter(private val model: SingleSubscribedModel, private val crashlyticsWrapper:
CrashlyticsWrapper, val creator: MediaSourceCreator, private val player: Player?) : ViewModel() {

    var view: SingleSubscribedPodcastView? = null

    private var startedWithTransition: Boolean = false

    private var podcast: SinglePodcast? = null

    fun onStart(view: SingleSubscribedPodcastView) {
        this.view = view
        model.subscribe(podcast, object : Observer<SinglePodcastRealm> {
            override fun onError(e: Throwable?) {}

            override fun onCompleted() {}

            override fun onNext(singlePodcastRealm: SinglePodcastRealm?) {
                if (view != null && singlePodcastRealm != null && singlePodcastRealm.episodes != null) {
                    view.setEpisodes(singlePodcastRealm.episodes)
                }
            }

        })
    }

    fun onStop() {
        this.view = null
    }

    fun startPresenter(podcast: SinglePodcast?, startedWithTransition: Boolean) {
        this.podcast = podcast
        this.startedWithTransition = startedWithTransition
        if (startedWithTransition) {
            view?.enterWithTransition()
        } else {
            view?.enterWithoutTransition()
        }
        view?.setTitle(podcast?.collectionName)
    }

    fun onOptionsItemSelected(itemId: Int): Boolean {
        return true

    }

}
