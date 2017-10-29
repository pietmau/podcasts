package com.pietrantuono.podcasts.repository.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import io.reactivex.Observable


interface PodcastRepo {
    fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast>
    fun getSubscribedPodcasts(): Observable<List<Podcast>>
    fun getIfSubscribed(podcast: Podcast?): Observable<Boolean>
    fun subscribeUnsubscribeToPodcast(podcast: Podcast?)
    fun getPodcastByEpisodeSync(episode: Episode): Podcast?
    fun savePodcastSync(podcast: Podcast)
}
