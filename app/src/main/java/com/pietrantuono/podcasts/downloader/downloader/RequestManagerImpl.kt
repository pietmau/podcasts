package com.pietrantuono.podcasts.downloader.downloader

import android.webkit.URLUtil

import com.tonyodev.fetch.request.Request
import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import repo.repository.EpisodesRepository
import javax.inject.Inject


class RequestManagerImpl
@Inject constructor(
        private val directoryProvider: DirectoryProvider,
        private val repository: EpisodesRepository) : RequestManager {

    private val requests: MutableMap<Long, RequestInfo?> = mutableMapOf<Long, RequestInfo?>()

    override fun cacheRequest(pair: Pair<Long, RequestInfo?>) {
        requests[pair.first] = pair.second
    }

    override fun getRequestById(id: Long): RequestInfo? = requests[id]

    /**  Called by the downloader service, in its own process  */
    override fun createRequestForDownload(title: String): Request? {
        val episode = repository.getEpisodeByTitleSync(title)
        return getRequest(episode)
    }

    private fun getRequest(episode: Episode?): Request? {
        if (episode != null) {
            val enclosures = episode.enclosures
            if (enclosures != null && !enclosures!!.isEmpty()) {
                return enclosures!![0].url?.let {
                    makeRequest(it)
                }
            }
        }
        return null
    }

    private fun makeRequest(url: String): Request {
        val dir = directoryProvider.downloadDir
        val filname = URLUtil.guessFileName(url, null, null)
        return Request(url, dir, filname)
    }

//    override fun createRequestForDeletion(episode: Episode): Request? {
//        val link = episode.link
//        val path = episode.filePathIncludingFileName
//        if (link == null || path == null) {
//            return null
//        }
//        return Request(link, path)
//    }

}

