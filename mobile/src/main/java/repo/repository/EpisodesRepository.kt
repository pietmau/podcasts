package repo.repository

import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import models.pojos.RealmEpisode
import rx.Observable


interface EpisodesRepository {
    fun getEpisodeByTitleSync(url: String?): Episode?
    fun getEpisodeByTitleAsObservable(url: String): Observable<out Episode?>
    fun getEpisodeByEnclosureUrlSync(url: String?): Episode?
    fun saveEpisodeSync(episode: RealmEpisode)
    fun setEpisodeNotDownloadedSync(id: Long)
    fun getEpisodeByDownloadIdSync(id: Long): Episode?
    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long)
}