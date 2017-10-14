package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import com.pietrantuono.podcasts.repository.EpisodesRepository

class DownloaderImpl(private val context: Context, private val repo: EpisodesRepository) : Downloader {

    override fun downloadIfAppropriate(podcast: Podcast?) {
        val episodes = podcast?.episodes?.
                map { it.link }?.
                filterNotNull()?.
                toList()
        val tracks = ArrayList(episodes)
        val intent = Intent(context, DownloaderService::class.java).apply {
            putStringArrayListExtra(DownloaderService.TRACK_LIST, tracks)
        }
        context.startService(intent)
    }


}