package com.pietrantuono.podcasts.subscribedpodcasts.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast

import rx.Observer

interface SubscribedPodcastModel {
    fun subscribeToSubscribedPodcasts(observer: Observer<List<Podcast>>)
    fun unsubscribe()
}
