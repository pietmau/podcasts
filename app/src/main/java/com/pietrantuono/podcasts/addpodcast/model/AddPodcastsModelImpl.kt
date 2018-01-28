package com.pietrantuono.podcasts.addpodcast.model

import com.pietrantuono.podcasts.addpodcast.singlepodcast.view.State
import rx.Observable
import rx.Observer
import rx.Subscription

class AddPodcastsModelImpl(private val searchApi: SearchApi) : AddPodcastsModel {
    internal var subscription: Subscription? = null
    private var observer: Observer<SearchResult>? = null
    internal var cachedRequest: Observable<SearchResult>? = null
    override var viewState: State? = null
    override var query: String? = null

    override fun subscribeToSearch(observer: Observer<SearchResult>) {
        this.observer = observer
        subscription = cachedRequest?.subscribe(observer)
    }

    override fun unsubscribeFromSearch() {
        if (subscription?.isUnsubscribed == false) {
            subscription?.unsubscribe()
        }
    }

    override fun searchPodcasts(query: String?) {
        this.query = query
        cachedRequest = searchApi.search(query)
        subscription = cachedRequest?.subscribe(observer)
    }

    override fun refresh() {
        query?.let { searchPodcasts(it) }
    }
}
