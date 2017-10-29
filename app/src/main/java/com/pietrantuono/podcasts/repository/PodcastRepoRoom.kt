package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.repository.PodcastRepo
import rx.Observable

class PodcastRepoRoom : PodcastRepo {
    override fun getPodcastByIdAsync(trackId: Int): Observable<out Podcast> {
        TODO("not implemented")
    }

    override fun getSubscribedPodcasts(): Observable<List<Podcast>> {
        TODO("not implemented")
    }

    override fun getIfSubscribed(podcast: Podcast?): Observable<Boolean> {
        TODO("not implemented")
    }

    override fun subscribeUnsubscribeToPodcast(podcast: Podcast?) {
        TODO("not implemented")
    }

    override fun getPodcastByEpisodeSync(episode: Episode): Podcast? {
        TODO("not implemented")
    }

    override fun savePodcastSync(podcast: Podcast) {
        TODO("not implemented")
    }

}