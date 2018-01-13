package com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter

import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.singlepodcast.model.SinglePodcastModel
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import com.pietrantuono.podcasts.addpodcast.singlepodcast.viewmodel.ResourcesProvider
import com.pietrantuono.podcasts.apis.PodcastFeed
import models.pojos.Podcast
import rx.Observer
import java.util.concurrent.TimeoutException

class SinglePodcastPresenter(
        private val model: SinglePodcastModel,
        private val crashlyticsWrapper: CrashlyticsWrapper,
        private val resources: ResourcesProvider
) : GenericPresenter, ViewModel() {
    private var view: SinglePodcastView? = null
    private var podcastFeed: PodcastFeed? = null
    private var startedWithTransition: Boolean? = false
    private val observer: SimpleObserver<Boolean>
    private var fromSavedState: Boolean = false
    private var timeOutTime: Long = 4

    init {
        observer = object : SimpleObserver<Boolean>() {
            override fun onNext(isSubscribedToPodcast: Boolean?) {
                view?.setSubscribedToPodcast(isSubscribedToPodcast)
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
        getFeed()
        model.subscribeToIsSubscribedToPodcast(observer)
    }

    private fun getFeed() {
        view?.showProgress(true)
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
        view?.showProgress(false)
        if (this.podcastFeed == null) {
            this.podcastFeed = podcastFeed
            setEpisodes()
        }
        model.saveFeed(podcastFeed)
    }

    private fun onCompleted() {
        view?.showProgress(false)
        view?.enablePullToRefresh(false)
    }

    private fun onError(throwable: Throwable?) {
        view?.showProgress(false)
        if (throwable is TimeoutException) {
            view?.onError(resources.getString(R.string.time_out))
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
        podcastFeed?.episodes?.let {
            view?.setEpisodes(it)
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

    fun onRefresh() {
        timeOutTime++
        getFeed()
    }

}


