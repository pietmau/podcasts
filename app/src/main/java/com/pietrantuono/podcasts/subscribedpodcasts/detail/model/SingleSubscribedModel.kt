package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import rx.Observer


abstract class SingleSubscribedModel {
    abstract fun subscribe(trackId: Int, observer: Observer<in Podcast>)
    abstract fun unsubscribe()
    abstract fun unsubscribeFromPodcast()
    abstract fun onDownLoadAllSelected()
}