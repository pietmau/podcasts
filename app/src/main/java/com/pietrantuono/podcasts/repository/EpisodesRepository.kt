package com.pietrantuono.podcasts.repository

import com.pietrantuono.podcasts.apis.Episode
import rx.Observable


interface EpisodesRepository {
    fun getEpisodeByUrlSync(url: String?): Episode?
    fun onDownloadCompleted(episode: Episode?)
    fun getEpisodeByUrlAsync(url: String?): Observable<out Episode>
    fun getEpisodeByUrlAsObservable(url: String?): Observable<out Episode?>
    fun getEpisodeByEnclosureUrlSync(url: String): Episode?
}