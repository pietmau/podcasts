package com.pietrantuono.podcasts.singlepodcast.presenter


import com.pietrantuono.CrashlyticsWrapper
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.singlepodcast.view.SinglePodcastView

import rx.Observer

class SinglePodcastPresenter(private val model: SinglePodcastModel, private val crashlyticsWrapper: CrashlyticsWrapper) : GenericPresenter {
    private var view: SinglePodcastView? = null
    private var podcastFeed: PodcastFeed? = null
    private var startedWithTransition: Boolean = false
    private val observer: SimpleObserver<Boolean>

    init {
        observer = object : SimpleObserver<Boolean>() {
            override fun onNext(isSubscribedToPodcast: Boolean?) {
                view!!.setSubscribedToPodcast(isSubscribedToPodcast)
            }
        }
    }

    override fun onDestroy() {
        view = null
    }

    override fun onPause() {
        model.unsubscribe()
    }

    override fun onResume() {
        model.subscribeToFeed(object : Observer<PodcastFeed> {
            override fun onCompleted() {
                view!!.showProgress(false)
            }

            override fun onError(e: Throwable) {
                crashlyticsWrapper.logException(e)
                view!!.showProgress(false)
            }

            override fun onNext(podcastFeed: PodcastFeed) {
                if (this@SinglePodcastPresenter.podcastFeed == null) {
                    this@SinglePodcastPresenter.podcastFeed = podcastFeed
                    setEpisodes()
                }
            }
        })
        model.subscribeToIsSubscribedToPodcast(observer)
    }

    fun bindView(view: SinglePodcastView) {
        this.view = view
        setEpisodes()
    }

    fun init(podcast: SinglePodcast, startedWithTransition: Boolean) {
        this.startedWithTransition = startedWithTransition
        model.init(podcast)
        if (startedWithTransition) {
            view!!.enterWithTransition()
        } else {
            view!!.enterWithoutTransition()
        }
    }

    private fun setEpisodes() {
        if (view == null || podcastFeed == null) {
            return
        }
        view!!.setEpisodes(podcastFeed!!.episodes)
    }

    fun onBackPressed() {
        if (startedWithTransition) {
            view!!.exitWithSharedTrsnsition()
        } else {
            view!!.exitWithoutSharedTransition()
        }
    }

    fun onSubscribeUnsubscribeToPodcastClicked() {
        model.onSubscribeUnsubscribeToPodcastClicked()
    }

    fun onDownloadAllPressed() {

    }

    fun onListenToAllPressed() {
        view?.listenToAll(podcastFeed);
    }

    companion object {
        val TAG = SinglePodcastPresenter::class.java.simpleName
    }
}
