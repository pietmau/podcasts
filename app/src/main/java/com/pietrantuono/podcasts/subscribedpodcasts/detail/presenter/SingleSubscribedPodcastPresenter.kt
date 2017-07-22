package com.pietrantuono.podcasts.subscribedpodcasts.detail.presenter

import android.arch.lifecycle.ViewModel
import com.pietrantuono.podcasts.CrashlyticsWrapper
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.SinglePodcastView
import com.pietrantuono.podcasts.apis.PodcastFeed
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.service.Player
import com.pietrantuono.podcasts.subscribedpodcasts.detail.model.SingleSubscribedModel
import rx.Observer

class SingleSubscribedPodcastPresenter(private val model: SingleSubscribedModel, private val crashlyticsWrapper:
CrashlyticsWrapper, val creator: MediaSourceCreator, private val player: Player?) : GenericPresenter, ViewModel() {
    companion object {
        val TAG = SingleSubscribedPodcastPresenter::class.java.simpleName
    }

    private var view: SinglePodcastView? = null
    private var podcastFeed: PodcastFeed? = null
    private var startedWithTransition: Boolean = false
    private val observer: SimpleObserver<Boolean>

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

    override fun onStop() {
        model.unsubscribe()
    }

    override fun onStart() {
        model.subscribeToFeed(object : Observer<PodcastFeed> {
            override fun onCompleted() {
                view?.showProgress(false)
            }

            override fun onError(throwable: Throwable?) {
                crashlyticsWrapper.logException(throwable)
                view?.showProgress(false)
            }

            override fun onNext(podcastFeed: PodcastFeed?) {
                if (this@SingleSubscribedPodcastPresenter.podcastFeed == null) {
                    this@SingleSubscribedPodcastPresenter.podcastFeed = podcastFeed
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

    fun startPresenter(podcast: SinglePodcast?, startedWithTransition: Boolean) {
        this.startedWithTransition = startedWithTransition
        model.startModel(podcast)
        if (startedWithTransition) {
            view?.enterWithTransition()
        } else {
            view?.enterWithoutTransition()
        }
        view?.setTitle(podcast?.collectionName)
    }

    private fun setEpisodes() {
        if (podcastFeed != null) {
            view?.setEpisodes(podcastFeed?.episodes)
        }
    }

    fun onBackPressed(): Boolean {
        if (startedWithTransition) {
            view?.exitWithSharedTrsnsition()
        } else {
            view?.exitWithoutSharedTransition()
        }
        return true;
    }

    fun onSubscribeUnsubscribeToPodcastClicked() {
        model.onSubscribeUnsubscribeToPodcastClicked()
    }

    fun onDownloadAllPressed(): Boolean {
        return true
    }

    fun onListenToAllPressed() {
        player?.playFeed(creator.createSourceFromFeed(podcastFeed))
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
                onSubscribeUnsubscribeToPodcastClicked()
                return true
            }
        }
        return false
    }

}
