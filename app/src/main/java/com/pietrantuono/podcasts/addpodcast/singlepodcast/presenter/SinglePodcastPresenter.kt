package com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter

import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.AddSinglePodcastActivity
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import com.pietrantuono.podcasts.apis.PodcastFeed
import models.pojos.Podcast
import rx.Observer
import java.util.concurrent.TimeoutException

class SinglePodcastPresenter(
        private val model: SinglePodcastModel
) : GenericPresenter, ViewModel() {
    private var view: SinglePodcastView? = null
    private var startedWithTransition: Boolean? = false
    private var fromSavedState: Boolean = false
    private var timeOutTime: Long = 4

    override fun onDestroy() {
        view = null
    }

    override fun onPause() {
        model.unsubscribe()
    }

    override fun onResume() {
        getFeed()
        model.subscribeToIsSubscribedToPodcast(object : SimpleObserver<Boolean>() {
            override fun onNext(isSubscribedToPodcast: Boolean?) {
                view?.setSubscribedToPodcast(isSubscribedToPodcast)
            }
        })
    }

    private fun getFeed() {
        if (model.alreadyAttemptedToGetFeed) {
            return
        }
        view?.setState(AddSinglePodcastActivity.State.LOADING)
        model.subscribeToFeed(object : Observer<PodcastFeed> {
            override fun onCompleted() {
                this@SinglePodcastPresenter.onCompleted()
            }

            override fun onError(throwable: Throwable?) {
                this@SinglePodcastPresenter.onError(throwable)
            }

            override fun onNext(podcastFeed: PodcastFeed?) {
                this@SinglePodcastPresenter.onNext(podcastFeed)
            }
        }, timeOutTime)
    }

    private fun onNext(podcastFeed: PodcastFeed?) {
        if (model.podcastFeed == null) {
            model.podcastFeed = podcastFeed
            setEpisodes()
        }
        model.saveFeed(podcastFeed)
    }

    private fun onCompleted() {
        model.alreadyAttemptedToGetFeed = true
        if (model.hasEpisodes) {
            view?.setState(AddSinglePodcastActivity.State.FULL)
            return
        }
        view?.setState(AddSinglePodcastActivity.State.EMPTY)
    }

    private fun onError(throwable: Throwable?) {
        model.alreadyAttemptedToGetFeed = true
        view?.setState(AddSinglePodcastActivity.State.ERROR)
        if (throwable is TimeoutException) {
            view?.onTimeout()
            return
        }
        view?.onError(throwable?.localizedMessage)
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
        model.podcastFeed?.episodes?.let {
            view?.setEpisodes(it)
            onCompleted()
        }
    }

    fun onBackPressed(): Boolean {
        if (startedWithTransition == true && !fromSavedState) {
            view?.exitWithSharedTrsnsition()
        } else {
            view?.exitWithoutSharedTransition()
        }
        return true
    }


    fun onOptionsItemSelected(itemId: Int): Boolean {
        when (itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.subscribe_unsubscribe -> {
                model.onSubscribeUnsubscribeToPodcastClicked()
                return true
            }
        }
        return false
    }

    fun onUsereRequestedRefresh() {
        model.alreadyAttemptedToGetFeed = false
        timeOutTime++
        getFeed()
    }
}


