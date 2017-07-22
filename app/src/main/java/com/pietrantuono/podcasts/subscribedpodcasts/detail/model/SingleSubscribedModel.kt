package com.pietrantuono.podcasts.subscribedpodcasts.detail.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.SinglePodcast
import com.pietrantuono.podcasts.addpodcast.singlepodcast.presenter.SimpleObserver
import com.pietrantuono.podcasts.apis.PodcastFeed
import rx.Observer


interface SingleSubscribedModel {
    fun unsubscribe()
    fun subscribeToFeed(observer: Observer<PodcastFeed>)
    fun subscribeToIsSubscribedToPodcast(observer: SimpleObserver<Boolean>)
    fun startModel(podcast: SinglePodcast?)
    fun onSubscribeUnsubscribeToPodcastClicked()
}