package com.pietrantuono.podcasts.addpodcast.presenter

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import com.pietrantuono.podcasts.GenericPresenter
import com.pietrantuono.podcasts.addpodcast.customviews.PodcastsAdapter
import com.pietrantuono.podcasts.addpodcast.model.AddPodcastsModel
import com.pietrantuono.podcasts.addpodcast.model.SearchResult
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastFragment
import com.pietrantuono.podcasts.addpodcast.view.AddPodcastView
import com.pietrantuono.podcasts.addpodcast.view.ApiLevelChecker
import models.pojos.Podcast
import rx.Observer

class AddPodcastPresenter(
        private val model: AddPodcastsModel,
        private val apiLevelChecker: ApiLevelChecker)
    : ViewModel(), GenericPresenter, PodcastsAdapter.OnSunscribeClickedListener, PodcastsAdapter.OnItemClickedClickedListener {

    private val observer: Observer<SearchResult>
    private var view: AddPodcastView? = null
    private var cachedResult: SearchResult? = null
    internal val viewState: State?
        get() = model.viewState

    init {
        observer = object : SimpleObserver<SearchResult>() {
            override fun onError(throwable: Throwable) {
                onError()
            }

            override fun onNext(result: SearchResult) {
                onSearchResultsAvailable(result)
            }
        }
    }

    private fun onError() {
        setViewState(State.ERROR)
    }

    private fun onSearchResultsAvailable(result: SearchResult) {
        if (result != cachedResult) {
            cachedResult = result
            updateSearchResults(result)
        }
        setViewEmptyOrFull(result)
    }

    private fun updateSearchResults(result: SearchResult) {
        view?.updateSearchResults(result.list)
        setViewEmptyOrFull(result)
    }

    private fun setViewEmptyOrFull(result: SearchResult) {
        if (shouldBeEmpty(result)) {
            setViewState(State.EMPTY)
            return
        }
        setViewState(State.FULL)
    }

    private fun setViewState(state: State) {
        model.viewState = state
        view?.setState(state)
    }

    private fun shouldBeEmpty(result: SearchResult): Boolean = result.list == null || result.list.isEmpty()

    fun bindView(addPodcastView: AddPodcastView, bundle: Bundle?) {
        this.view = addPodcastView
        setSavedViewState(bundle)
        cachedResult?.let {
            updateSearchResults(it)
        }
    }

    private fun setSavedViewState(bundle: Bundle?) {
        val state = getViewState(bundle)
        setViewState(state)
    }

    private fun getViewState(bundle: Bundle?) = (bundle?.getSerializable(AddPodcastFragment.STATE) as? State) ?: State.EMPTY

    override fun onDestroy() {
        view = null
    }

    override fun onPause() {
        model.unsubscribeFromSearch()
    }

    override fun onResume() {
        model.subscribeToSearch(observer)
    }

    fun onQueryTextSubmit(query: String?): Boolean {
        model.searchPodcasts(query)
        return true
    }

    override fun onSubscribeClicked(podcast: Podcast) = throw UnsupportedOperationException()

    override fun onItemClicked(podcast: Podcast, imageView: ImageView, position: Int, titleContainer: LinearLayout) {
        if (apiLevelChecker.isLollipopOrHigher && view?.isPartiallyHidden(position) == false) {
            view?.startDetailActivityWithTransition(podcast, imageView, titleContainer)
        } else {
            view?.startDetailActivityWithoutTransition(podcast)
        }
    }

    fun thereIsAValidQuery() = !model.query.isNullOrBlank()

    fun refresh() {
        setViewState(State.LOADING)
        model.refresh()
    }
}
