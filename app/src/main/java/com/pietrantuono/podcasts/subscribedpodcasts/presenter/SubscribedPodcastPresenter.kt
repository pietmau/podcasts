package com.pietrantuono.podcasts.subscribedpodcasts.presenter

import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.subscribedpodcasts.model.SubscribedPodcastModel
import com.pietrantuono.podcasts.subscribedpodcasts.view.SubscribedPodcastView

class SubscribedPodcastPresenter(private val model: SubscribedPodcastModel, private val apiLevelChecker:
ApiLevelChecker) : GenericPresenter, PodcastsAdapter.OnItemClickedClickedListener {

    override fun onDestroy() { }

    override fun onStop() {}

    private var view: SubscribedPodcastView? = null

    fun bindView(view: SubscribedPodcastView) {
        this.view = view
    }

    override fun onStart() {
        model.subscribeToSubscribedPodcasts(object : SimpleObserver<List<SinglePodcast>>() {
            override fun onError(throwable: Throwable) {
                view!!.onError(throwable)
            }
            override fun onNext(podcasts: List<SinglePodcast>?) {
                if (podcasts != null || !podcasts!!.isEmpty()) {
                    view!!.setPodcasts(podcasts)
                }
            }
        })
    }

    override fun onItemClicked(singlePodcast: SinglePodcast?, imageView: ImageView?, position: Int,
                               titleContainer: LinearLayout?) {
        if (apiLevelChecker.isLollipopOrHigher && !view!!.isPartiallyHidden(position)) {
            view!!.startDetailActivityWithTransition(singlePodcast, imageView, titleContainer)
        } else {
            view!!.startDetailActivityWithoutTransition(singlePodcast)
        }
    }

    companion object {
        val TAG = SubscribedPodcastPresenter::class.java.simpleName
    }


}
