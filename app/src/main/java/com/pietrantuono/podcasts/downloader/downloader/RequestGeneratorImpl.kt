package com.pietrantuono.podcasts.downloader.downloader

import android.webkit.URLUtil
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.request.Request
import javax.inject.Inject


class RequestGeneratorImpl @Inject constructor(
        private val directoryProvider: DirectoryProvider,
        private var repository: EpisodesRepository) : RequestGenerator {

    /**  Called by the downloader service, in its own process  */
    override fun createRequest(url: String): Request? {
        val episode = repository.getEpisodeByUrl(url)
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

}

