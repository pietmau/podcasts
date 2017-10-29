package com.pietrantuono.podcasts.addpodcast.singlepodcast.model


import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.PodcastFeed
import io.reactivex.observers.DisposableObserver

interface SinglePodcastModel {

    fun subscribeToFeed(observer: DisposableObserver<PodcastFeed>)

    fun unsubscribe()

    fun subscribeToIsSubscribedToPodcast(observer: DisposableObserver<Boolean>)

    fun startModel(podcast: Podcast?)

    fun onSubscribeUnsubscribeToPodcastClicked()
}
