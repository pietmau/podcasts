package repo.repository

import com.tonyodev.fetch.request.RequestInfo
import io.realm.Realm
import pojos.Episode
import pojos.RealmEpisode
import rx.Observable

class EpisodesRepositoryRealm(private val cache: EpisodeCache) : EpisodesRepository {


    val DOWNLOAD_REQUEST_ID = "downloadRequestId"
    private val LINK = "link"
    private val ENCLOSURE_URL = "syndEnclosures.url"

    override fun setEpisodeNotDownloadedSync(id: Long) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                val episode = it.where(RealmEpisode::class.java)
                        .equalTo(DOWNLOAD_REQUEST_ID, id)
                        .findFirst()
                episode.downloaded = false
            }
        }
    }

    override fun onDownloadCompleted(requestInfo: RequestInfo, downloadedBytes: Long) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.where(RealmEpisode::class.java)
                        .contains(ENCLOSURE_URL, requestInfo.url)
                        .findFirst()?.let {
                    it.downloaded = true
                    it.filePathIncludingFileName = requestInfo.filePath
                    it.fileSizeInBytes = downloadedBytes
                    it.downloadRequestId = requestInfo.id
                }
            }
        }
    }

    override fun getEpisodeByDownloadIdSync(id: Long): Episode =
            Realm.getDefaultInstance().use {
                it.where(RealmEpisode::class.java)
                        .equalTo(DOWNLOAD_REQUEST_ID, id)
                        .findFirst()
            }

    override fun getEpisodeByUrlAsync(url: String): Observable<out Episode> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(RealmEpisode::class.java)
                        .equalTo(LINK, url)
                        .findFirstAsync()//TODO dio can
                        .asObservable<RealmEpisode>()
                        .filter { it.isLoaded && it.isValid }
                        .filter { it != null }
                        .map { realm.copyFromRealm(it) }
            }

    /** To be used from another Thread or from a service in another process . */
    override fun saveEpisodeSync(episode: RealmEpisode) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                it.copyToRealmOrUpdate(episode)
            }
        }
    }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByUrlSync(url: String?): Episode? = url?.let { url ->
        cache.getEpisodeByUrls(url) ?: Realm.getDefaultInstance().use { realm ->
            realm.where(RealmEpisode::class.java)
                    .equalTo(LINK, url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                        cache.cacheEpisodeByUrls(url, episode)
                    }
        }
    }


    override fun getEpisodeByUrlAsObservable(url: String): Observable<out Episode> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(RealmEpisode::class.java)
                        .equalTo(LINK, url)
                        .findFirst()
                        .asObservable<RealmEpisode>()
                        .filter { it != null }
                        .map { it as RealmEpisode }
                        .filter { it.isLoaded && it.isValid }
                        .map { realm.copyFromRealm(it) }
            }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByEnclosureUrlSync(url: String?): Episode? = url?.let { url ->
        cache.getEpisodesByEnclosureUrl(url) ?: Realm.getDefaultInstance().use { realm ->
            realm.where(RealmEpisode::class.java)
                    .contains(ENCLOSURE_URL, url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                        cache.cacheEpisodeByEnclosureUrl(url, episode)
                    }
        }
    }

}
