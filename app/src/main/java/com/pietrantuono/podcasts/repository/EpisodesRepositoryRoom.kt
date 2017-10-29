package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import rx.Observable

class EpisodesRepositoryRoom(cache: EpisodeCache) : EpisodesRepository {
    override fun getEpisodeByUrlSync(url: String?): Episode? {
        TODO("not implemented")
    }

    override fun getEpisodeByUrlAsync(url: String): Observable<out Episode> {
        TODO("not implemented")
    }

    override fun getEpisodeByUrlAsObservable(url: String): Observable<out Episode?> {
        TODO("not implemented")
    }

    override fun getEpisodeByEnclosureUrlSync(url: String?): Episode? {
        TODO("not implemented")
    }

    override fun saveEpisodeSync(episode: Episode) {
        TODO("not implemented")
    }

}