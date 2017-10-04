package com.pietrantuono.podcasts.subscribedpodcasts.list.presenter


import android.arch.lifecycle.ViewModel
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.subscribedpodcasts.list.menu.EpisodesListMenuProvider
import com.pietrantuono.podcasts.subscribedpodcasts.list.menu.EpisodesListMenuProviderImpl
import com.pietrantuono.podcasts.subscribedpodcasts.list.model.EpisodesListModel
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodedListRecycler
import com.pietrantuono.podcasts.subscribedpodcasts.list.views.EpisodesListView

class EpisodesListPresenter(private val model: EpisodesListModel, private val menuProvider: EpisodesListMenuProviderImpl,
                            private val apiLevelChecker: ApiLevelChecker)
    : ViewModel(), EpisodesListMenuProvider by menuProvider, EpisodedListRecycler.OnItemClickListener {

    private var view: EpisodesListView? = null
    private var startedWithTransition: Boolean = false
    private var fromSavedState: Boolean = false
    val menuInflater: MenuInflater?
        get() = view?.getMenuInflater()

    fun onStart(view: EpisodesListView, trackId: Int, startedWithTransition: Boolean) {
        menuProvider.setCallback(this@EpisodesListPresenter)
        this.view = view
        startPresenter(startedWithTransition)
        model.subscribe(trackId, object : SimpleObserver<Podcast>() {
            override fun onNext(feed: Podcast?) {
                if (view != null && feed != null && feed.episodes != null) {
                    view.setEpisodes(feed.episodes!!)
                    view.title = feed.collectionName
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

    override fun onItemClicked(episode: Episode, image: ImageView, position: Int) {
        view?.startDetailActivityWithoutTransition(episode)
    }

    fun onBackPressed() {
        if (startedWithTransition && !fromSavedState) {
            view?.exitWithSharedTrsnsition()
        }
        else{
            view?.exitWithoutSharedTransition()
        }
    }

    fun onCreate(fromSavedState: Boolean) {
        this.fromSavedState = fromSavedState
    }
}
