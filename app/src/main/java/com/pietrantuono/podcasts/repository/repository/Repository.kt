package com.pietrantuono.podcasts.repository.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast

import rx.Observable
import rx.Observer

interface Repository {
    fun getIfSubscribed(trackId: Podcast): Observable<Boolean>
    fun getSubscribedPodcasts(observer: Observer<List<Podcast>>): Observable<List<Podcast>>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast)
    fun getPodcastById(trackId: Int): Observable<out Podcast>
}
