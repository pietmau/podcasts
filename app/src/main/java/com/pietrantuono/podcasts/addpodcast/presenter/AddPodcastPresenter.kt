package com.pietrantuono.podcasts.addpodcast.presenter

import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel
import com.pietrantuono.podcasts.addpodcast.model.SearchResult
import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragmentMemento
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import rx.Observer

class AddPodcastPresenter(private val addPodcastsModel: AddPodcastsModel, private val apiLevelChecker: ApiLevelChecker) : GenericPresenter, PodcastsAdapter.OnSunscribeClickedListener, PodcastsAdapter.OnItemClickedClickedListener {
    private val observer: Observer<SearchResult>
    private var addPodcastView: AddPodcastView? = null
    private var cachedResult: SearchResult? = null

    init {
        observer = object : Observer<SearchResult> {

            override fun onCompleted() {
                showProgressBar(false)
            }

            override fun onError(e: Throwable) {
                if (addPodcastView != null) {
                    addPodcastView!!.onError(e)
                }
                showProgressBar(false)
            }

            override fun onNext(result: SearchResult) {
                if (addPodcastView != null && result != cachedResult) {
                    cachedResult = result
                    addPodcastView!!.updateSearchResults(cachedResult!!.list)
                }
            }
        }
    }

    fun bindView(addPodcastView: AddPodcastView, memento: AddPodcastFragmentMemento) {
        this.addPodcastView = addPodcastView
        addPodcastView.showProgressBar(memento.isProgressShowing)
        if (cachedResult != null) {
            addPodcastView.updateSearchResults(cachedResult!!.list)
        }
    }

    override fun onDestroy() {
        addPodcastView = null
    }

    override fun onPause() {
        addPodcastsModel.unsubscribeFromSearch()
    }

    override fun onResume() {
        addPodcastsModel.subscribeToSearch(observer)
    }

    fun onQueryTextSubmit(query: String): Boolean {
        launchQuery(query)
        return true
    }

    private fun launchQuery(query: String) {
        addPodcastsModel.searchPodcasts(query)
        showProgressBar(true)
    }

    fun onQueryTextChange(newText: String): Boolean {
        addPodcastView!!.onQueryTextChange(newText)
        return true
    }

    fun onSaveInstanceState(memento: AddPodcastFragmentMemento) {
        memento.isProgressShowing = addPodcastView!!.isProgressShowing
    }

    private fun showProgressBar(show: Boolean) {
        if (addPodcastView != null) {
            addPodcastView!!.showProgressBar(show)
        }
    }

    override fun onSubscribeClicked(singlePodcast: SinglePodcast) {}

    override fun onItemClicked(singlePodcast: SinglePodcast, imageView: ImageView, position: Int, titleContainer: LinearLayout, barcolor: Int) {
        if (apiLevelChecker.isLollipopOrHigher && !addPodcastView!!.isPartiallyHidden(position)) {
            addPodcastView!!.startDetailActivityWithTransition(singlePodcast, imageView, titleContainer, barcolor)
        } else {
            addPodcastView!!.startDetailActivityWithoutTransition(singlePodcast)
        }
    }

    companion object {
        val TAG = AddPodcastPresenter::class.java.simpleName
    }
}
