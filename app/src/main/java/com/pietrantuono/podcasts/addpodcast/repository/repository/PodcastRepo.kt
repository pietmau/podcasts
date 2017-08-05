package com.pietrantuono.podcasts.addpodcast.repository.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import rx.Observable
import rx.Observer


interface PodcastRepo {
    fun getPodcastById(trackId: Int): Observable<out Podcast>
    fun getSubscribedPodcasts(observer: Observer<List<Podcast>>): Observable<List<Podcast>>
    fun getIfSubscribed(podcast: Podcast?): Observable<Boolean>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast?)
}
