package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import io.reactivex.Observable


interface EpisodesRepository {
    fun getEpisodeByUrlSync(url: String?): Episode?
    fun getEpisodeByUrlAsync(url: String): Observable<out Episode>
    fun getEpisodeByUrlAsObservable(url: String): Observable<out Episode?>
    fun getEpisodeByEnclosureUrlSync(url: String?): Episode?
    fun saveEpisodeSync(episode: Episode)
}