package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import rx.Observer


abstract class SingleSubscribedModel {
    abstract fun subscribe(trackId: Int, observer: Observer<in SinglePodcast>)
    abstract fun unsubscribe()
}