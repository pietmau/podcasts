package com.pietrantuono.podcasts.downloader

import android.content.Context
import com.pietrantuono.podcasts.apis.PodcastEpisode
import javax.inject.Inject

class PodcastDownLoadManager @Inject constructor(private val context: Context) {

    fun downLoadAll(episodes: List<PodcastEpisode>) {
        val tracks = mutableListOf<String>()
        for (episode in episodes) {
            //episode.
        }
    }
}