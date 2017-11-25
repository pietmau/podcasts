package com.pietrantuono.podcasts.subscribedpodcasts.list.model

import pojos.Podcast
import rx.Observer

abstract class EpisodesListModel {
    abstract fun subscribe(trackId: Int, observer: Observer<in Podcast>)
    abstract fun unsubscribe()
    abstract fun unsubscribeFromPodcast()
    abstract fun onDownLoadAllSelected()
}