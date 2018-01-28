package com.pietrantuono.podcasts.addpodcast.model


import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State
import rx.Observer

interface AddPodcastsModel {
    var viewState: State?

    var query: String?

    fun subscribeToSearch(observer: Observer<SearchResult>)

    fun unsubscribeFromSearch()

    fun searchPodcasts(query: String?)

    fun refresh()
}
