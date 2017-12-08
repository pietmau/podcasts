package repo.repository

import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import models.pojos.RealmEpisode
import rx.Observable


interface EpisodesRepository {
    fun getEpisodeByUriSync(url: String?): Episode?
    fun getEpisodeByUriAsObservable(url: String): Observable<out Episode?>
    fun getEpisodeByEnclosureUrlSync(url: String?): Episode?
    fun saveEpisodeSync(episode: RealmEpisode)
    fun setEpisodeDeletedAndNotDowloaded(id: Long)
    fun getEpisodeByDownloadIdSync(id: Long): Episode?
    fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long)
    fun saveRequestId(uri: String, requestId: Long)
}