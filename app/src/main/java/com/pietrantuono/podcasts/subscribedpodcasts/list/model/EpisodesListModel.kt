package com.pietrantuono.podcasts.subscribedpodcasts.list.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import io.reactivex.observers.DisposableObserver


abstract class EpisodesListModel {
    abstract fun subscribe(trackId: Int, observer: DisposableObserver<in Podcast>)
    abstract fun unsubscribe()
    abstract fun unsubscribeFromPodcast()
    abstract fun onDownLoadAllSelected()
}