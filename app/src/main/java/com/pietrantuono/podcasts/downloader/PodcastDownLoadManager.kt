package com.pietrantuono.podcasts.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.downloader.service.DownloaderService
import javax.inject.Inject

class PodcastDownLoadManager @Inject constructor(private val context: Context) {

    fun downLoadAll(episodes: List<Episode>) {
        val tracks = arrayListOf<String>()
        for (episode in episodes) {
            episode.link?.let { tracks.add(it) }
        }
        val intent = Intent(context, DownloaderService::class.java)
        intent.putStringArrayListExtra(DownloaderService.TRACK_LIST, tracks)
        context.startService(intent)
    }
}