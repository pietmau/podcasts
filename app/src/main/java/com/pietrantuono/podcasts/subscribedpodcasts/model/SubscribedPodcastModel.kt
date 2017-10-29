package com.pietrantuono.podcasts.subscribedpodcasts.model

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import io.reactivex.observers.DisposableObserver

interface SubscribedPodcastModel {
    fun subscribeToSubscribedPodcasts(observer: DisposableObserver<List<Podcast>>)
    fun unsubscribe()
}
