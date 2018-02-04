package com.pietrantuono.podcasts.subscribedpodcasts.presenter

import android.arch.lifecycle.ViewModel
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView
import models.pojos.Podcast

class SubscribedPodcastPresenter(
        private val model: SubscribedPodcastModel,
        private val apiLevelChecker: ApiLevelChecker) :
        GenericPresenter, PodcastsAdapter.OnItemClickedClickedListener, ViewModel() {

    override fun onDestroy() {}

    override fun onPause() {
        model.unsubscribe()
    }

    private var view: SubscribedPodcastView? = null

    fun bindView(view: SubscribedPodcastView?) {
        this.view = view
    }

    override fun onResume() {
        model.subscribeToSubscribedPodcasts(object : SimpleObserver<List<Podcast>>() {
            override fun onError(throwable: Throwable) {
                view?.onError(throwable)
            }

            override fun onNext(podcasts: List<Podcast>?) {
                if (podcasts != null || !podcasts!!.isEmpty()) {
                    view?.setPodcasts(podcasts)
                }
            }
        })
    }

    override fun onItemClicked(podcast: Podcast?, imageView: ImageView?, position: Int,
                               titleContainer: LinearLayout?) {
        if (apiLevelChecker.isLollipopOrHigher && !isViewPartiallyHidden(position)) {
            view?.startDetailActivityWithTransition(podcast, imageView, titleContainer)
        } else {
            view?.startDetailActivityWithoutTransition(podcast)
        }
    }

    companion object {
        val TAG = SubscribedPodcastPresenter::class.java.simpleName
    }

    private fun isViewPartiallyHidden(position: Int): Boolean {
        if (view != null) {
            return view!!.isPartiallyHidden(position)
        } else {
            return false
        }
    }
}
