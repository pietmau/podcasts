package com.pietrantuono.podcasts.player.player.service

import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.PodcastFeedSource

interface Player {
    fun playFeed(source: PodcastFeedSource)
    fun playEpisode(episode: Episode)

    fun setMediaSource(source: MediaSource)
}