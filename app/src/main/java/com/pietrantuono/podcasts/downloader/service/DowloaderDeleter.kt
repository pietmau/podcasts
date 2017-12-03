package com.pietrantuono.podcasts.downloader.service

import android.content.Intent
import com.pietrantuono.podcasts.downloader.downloader.Fetcher
import com.tonyodev.fetch.request.RequestInfo


interface DowloaderDeleter {
    fun addCallback(downloaderService: Fetcher.Callback)
    fun deleteEpisode(id: Long)
    fun downloadEpisode(intent: Intent):Pair<Long, RequestInfo?>?
    fun downloadAllEpisodes(intent: Intent)
    fun deleteAllEpisodes(intent: Intent)
    fun shutDown()
    fun thereIsEnoughDiskSpace(fileSize: Long): Boolean
    val allDownlaodsAreCompleted: Boolean
}