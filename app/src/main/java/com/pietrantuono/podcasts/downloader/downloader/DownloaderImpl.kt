package com.pietrantuono.podcasts.downloader.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.addpodcast.model.pojos.Podcast
import com.pietrantuono.podcasts.downloader.service.DownloaderService

class DownloaderImpl(private val context: Context) : Downloader {

    override fun downloadEpisodeFromLink(link: String) {
        val intent = Intent(context, DownloaderService::class.java)
        intent.putExtra(DownloaderService.TRACK, link)
        context.startService(intent)
    }

    override fun downloadIfAppropriate(podcast: Podcast?) {
        podcast?.episodes?.
                map { it.link }?.
                filterNotNull()?.
                toList()?.let {
            val intent = Intent(context, DownloaderService::class.java)
            intent.putStringArrayListExtra(DownloaderService.TRACK_LIST, ArrayList(it))
            context.startService(intent)
        }

    }


}