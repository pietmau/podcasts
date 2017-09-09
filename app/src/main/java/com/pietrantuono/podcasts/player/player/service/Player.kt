package com.pietrantuono.podcasts.player.player.service

import android.support.v4.media.MediaDescriptionCompat
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.PodcastFeedSource

interface Player {
    fun playFeed(source: PodcastFeedSource)
    fun playEpisode(episode: MediaSource)
    fun setEpisode(episode: Episode)
    val media: MediaDescriptionCompat?
    fun pause()
    fun play()
}