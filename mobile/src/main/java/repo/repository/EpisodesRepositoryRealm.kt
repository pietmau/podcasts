package repo.repository

import com.tonyodev.fetch.request.RequestInfo
import io.realm.Realm
import models.pojos.Episode
import models.pojos.RealmEpisode
import rx.Observable

class EpisodesRepositoryRealm() : EpisodesRepository {

    val DOWNLOAD_REQUEST_ID = "downloadRequestId"
    private val URI = "uri"
    private val ENCLOSURE_URL = "syndEnclosures.url"

    override fun setEpisodeDeletedAndNotDowloaded(id: Long) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                val episode = it.where(RealmEpisode::class.java)
                        .equalTo(DOWNLOAD_REQUEST_ID, id)
                        .findFirst()
                episode?.downloaded = false
                episode?.deleted = true
            }
        }
    }

    override fun saveRequestId(uri: String, requestId: Long) {
        Realm.getDefaultInstance().use { realm ->
            realm.executeTransaction {
                val episode = it.where(RealmEpisode::class.java)
                        .equalTo(URI, uri)
                        .findFirst()
                episode?.downloadRequestId = requestId
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
                    it.deleted = false
                }
            }
        }
    }

    override fun getEpisodeByDownloadIdSync(id: Long): Episode? =
            Realm.getDefaultInstance().use {
                it.where(RealmEpisode::class.java)
                        .equalTo(DOWNLOAD_REQUEST_ID, id)
                        .findFirst()
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
    override fun getEpisodeByUriSync(uri: String?): Episode? = uri?.let { uri ->
        Realm.getDefaultInstance().use { realm ->
            var episode = realm.where(RealmEpisode::class.java)
                    .equalTo(URI, uri)
                    .findFirst()
            if (episode != null) {
                episode = realm.copyFromRealm(episode)
            }
            episode
        }
    }

    override fun getEpisodeByUriAsObservable(uri: String): Observable<out Episode> =
            Realm.getDefaultInstance().use { realm ->
                realm.where(RealmEpisode::class.java)
                        .equalTo(URI, uri)
                        .findFirstAsync()
                        .asObservable<RealmEpisode>()
                        .filter { it != null }
                        .filter { it.isLoaded && it.isValid }
                        .map { realm.copyFromRealm(it) }
            }

    /** To be used from another Thread or from a service in another process . */
    override fun getEpisodeByEnclosureUrlSync(url: String?): Episode? = url?.let { url ->
        Realm.getDefaultInstance().use { realm ->
            realm.where(RealmEpisode::class.java)
                    .contains(ENCLOSURE_URL, url)
                    .findFirst()
                    ?.also { reamlEpisode ->
                        val episode = realm.copyFromRealm(reamlEpisode)
                    }
        }
    }

}
