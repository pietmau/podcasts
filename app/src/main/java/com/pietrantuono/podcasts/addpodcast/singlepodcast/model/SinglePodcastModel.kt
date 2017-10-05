package com.pietrantuono.podcasts.addpodcast.singlepodcast.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastFeed

import rx.Observer

interface SinglePodcastModel {

    fun subscribeToFeed(observer: Observer<PodcastFeed>)

    fun unsubscribe()

    fun subscribeToIsSubscribedToPodcast(observer: Observer<Boolean>)

    fun startModel(podcast: Podcast?)

    fun onSubscribeUnsubscribeToPodcastClicked()
}