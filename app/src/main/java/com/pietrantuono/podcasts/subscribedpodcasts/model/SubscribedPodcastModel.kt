package com.pietrantuono.podcasts.subscribedpodcasts.model


import diocan.pojos.Podcast
import rx.Observer

interface SubscribedPodcastModel {
    fun subscribeToSubscribedPodcasts(observer: Observer<List<Podcast>>)
    fun unsubscribe()
}
