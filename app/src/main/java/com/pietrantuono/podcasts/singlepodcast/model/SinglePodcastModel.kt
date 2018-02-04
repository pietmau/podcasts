package com.pietrantuono.podcasts.addpodcast.singlepodcast.model


import com.pietrantuono.podcasts.apis.PodcastFeed
import models.pojos.Podcast

import rx.Observer

interface SinglePodcastModel {

    fun subscribeToFeed(observer: Observer<PodcastFeed>, timeOutTime: Long)

    fun unsubscribe()

    fun subscribeToIsSubscribedToPodcast(observer: Observer<Boolean>)

    fun startModel(podcast: Podcast?)

    fun onSubscribeUnsubscribeToPodcastClicked()

    fun saveFeed(podcastFeed: PodcastFeed?)

    var podcastFeed: PodcastFeed?

    val hasEpisodes: Boolean

    var alreadyAttemptedToGetFeed: Boolean
}
