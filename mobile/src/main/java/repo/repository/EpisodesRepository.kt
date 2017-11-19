package repo.repository

import com.tonyodev.fetch.request.RequestInfo
import pojos.Episode
import pojos.RealmEpisode
import rx.Observable


interface EpisodesRepository {
    fun getEpisodeByUrlSync(url: String?): Episode?
    fun getEpisodeByUrlAsync(url: String): Observable<out Episode>
    fun getEpisodeByUrlAsObservable(url: String): Observable<out Episode?>
    fun getEpisodeByEnclosureUrlSync(url: String?): Episode?
    fun saveEpisodeSync(episode: RealmEpisode)
    fun setEpisodeNotDownloadedSync(id: Long)
    fun getEpisodeByDownloadIdSync(id: Long): Episode?
    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long)
}