package com.pietrantuono.podcasts.downloader.downloader

import android.webkit.URLUtil
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository
import com.tonyodev.fetch.request.Request
import javax.inject.Inject


class RequestGeneratorImpl @Inject constructor(private val directoryProvider: DirectoryProvider,
                                               private var repository: EpisodesRepository) : RequestGenerator {
    override fun createRequest(url: String): Pair<Request, Episode>? {
        val episode = repository.getEpisodeByUrl(url)
        return getRequest(episode)?.let { Pair(it, episode!!) }
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

    private fun makeRequest(it: String): Request {
        val dir = directoryProvider.downloadDir
        val filname = URLUtil.guessFileName(it, null, null)
        return Request(it, dir, filname)
    }

}

