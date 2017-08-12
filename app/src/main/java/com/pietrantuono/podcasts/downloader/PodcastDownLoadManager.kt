package com.pietrantuono.podcasts.downloader

import android.content.Context
import android.content.Intent
import com.pietrantuono.podcasts.apis.PodcastEpisode
import com.pietrantuono.podcasts.downloader.service.DowloaderService
import javax.inject.Inject

class PodcastDownLoadManager @Inject constructor(private val context: Context) {

    fun downLoadAll(episodes: List<PodcastEpisode>) {
        val tracks = arrayListOf<String>()
        for (episode in episodes) {
            episode.link?.let { tracks.add(it) }
        }
        val intent = Intent(context, DowloaderService::class.java)
        intent.putStringArrayListExtra(DowloaderService.TRACK_LIST, tracks)
        context.startService(intent)
    }
}