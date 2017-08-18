package com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.presenter


import android.arch.lifecycle.ViewModel
import android.view.MenuItem
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.menu.EpisodesListMenuProvider
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.menu.EpisodesListMenuProviderImpl
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views.EpisodedListRecycler
import com.pietrantuono.podcasts.subscribedpodcasts.subscribedepisodeslist.views.EpisodesListView

class EpisodesListPresenter(private val model: EpisodesListModel, private val menuProvider: EpisodesListMenuProviderImpl,
                            private val apiLevelChecker: ApiLevelChecker)
    : ViewModel(), EpisodesListMenuProvider by menuProvider, EpisodedListRecycler.OnItemClickListener {

    var view: EpisodesListView? = null

    private var startedWithTransition: Boolean = false

    fun onStart(view: EpisodesListView, trackId: Int, startedWithTransition: Boolean) {
        menuProvider.setCallback(this@EpisodesListPresenter)
        this.view = view
        startPresenter(startedWithTransition)
        model.subscribe(trackId, object : SimpleObserver<Podcast>() {
            override fun onNext(feed: Podcast?) {
                if (view != null && feed != null && feed.episodes != null) {
                    view.setEpisodes(feed.episodes!!)
                    view.setTitle(feed.collectionName)
                }
            }
        })
    }

    fun onStop() {
        menuProvider.setCallback(null)
        this.view = null
        model.unsubscribe()
    }

    private fun startPresenter(startedWithTransition: Boolean) {
        this.startedWithTransition = startedWithTransition
        if (startedWithTransition) {
            view?.enterWithTransition()
        } else {
            view?.enterWithoutTransition()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return menuProvider.onOptionsItemSelected(item)
    }

    fun onDownLoadAllSelected() {
        model.onDownLoadAllSelected()
    }

    fun onItemClicked(podcast: Podcast?, imageView: ImageView?, position: Int,
                      titleContainer: LinearLayout?) {
        if (view == null) {
            return
        }
        if (apiLevelChecker.isLollipopOrHigher && !view!!.isPartiallyHidden(position)) {
            view?.startDetailActivityWithTransition(podcast, imageView, titleContainer)
        } else {
            view?.startDetailActivityWithoutTransition(podcast)
        }
    }

}
