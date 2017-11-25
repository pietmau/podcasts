package com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter

import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import com.pietrantuono.podcasts.apis.PodcastFeed
import pojos.Podcast
import rx.Observer

class SinglePodcastPresenter(
        private val model: SinglePodcastModel,
        private val crashlyticsWrapper: CrashlyticsWrapper) : GenericPresenter, ViewModel() {
    private var view: SinglePodcastView? = null
    private var podcastFeed: PodcastFeed? = null
    private var startedWithTransition: Boolean? = false
    private val observer: SimpleObserver<Boolean>
    private var fromSavedState: Boolean = false

    init {
        observer = object : SimpleObserver<Boolean>() {
            override fun onNext(isSubscribedToPodcast: Boolean?) {
                view?.setSubscribedToPodcast(isSubscribedToPodcast)
                if (isSubscribedToPodcast == true) {
                }
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
                view?.showProgress(false)
            }

            override fun onError(throwable: Throwable?) {
                crashlyticsWrapper.logException(throwable)
                view?.showProgress(false)
            }

            override fun onNext(podcastFeed: PodcastFeed?) {
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

    fun startPresenter(podcast: Podcast?, startedWithTransition: Boolean?, fromSavedState: Boolean) {
        this.startedWithTransition = startedWithTransition
        this.fromSavedState = fromSavedState
        model.startModel(podcast)
        if (startedWithTransition == true) {
            view?.enterWithTransition()
        } else {
            view?.enterWithoutTransition()
        }
        view?.title = podcast?.collectionName
    }

    private fun setEpisodes() {
        podcastFeed?.let {
            view?.setEpisodes(podcastFeed?.episodes)
        }
    }

    fun onBackPressed(): Boolean {
        if (startedWithTransition == true && !fromSavedState) {
            view?.exitWithSharedTrsnsition()
        } else {
            view?.exitWithoutSharedTransition()
        }
        return true;
    }

    fun onListenToAllPressed() {
        //player?.playFeed(creator.createSourceFromFeed(podcastFeed))
    }

    fun onOptionsItemSelected(itemId: Int): Boolean {
        when (itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.listen_to_all -> {
                onListenToAllPressed()
                return true
            }
            R.id.subscribe_unsubscribe -> {
                model.onSubscribeUnsubscribeToPodcastClicked()
                return true
            }
        }
        return false
    }

}


