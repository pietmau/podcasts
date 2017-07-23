package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.providers.SinglePodcastRealm
import rx.Observer


abstract class SingleSubscribedModel {

    abstract fun subscribe(podcast: SinglePodcast?, observer: Observer<SinglePodcastRealm>)
    abstract fun unsubscribe()
}