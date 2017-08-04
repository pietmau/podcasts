package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import rx.Observer


abstract class SingleSubscribedModel {

    abstract fun subscribe(trackId: Int, observer: Observer<SinglePodcastRealm>)
    abstract fun unsubscribe()
}