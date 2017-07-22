package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import rx.Observer


interface SingleSubscribedModel {
    fun subscribeToFeed(observer: Observer<PodcastFeed>, podcast: SinglePodcast)
}