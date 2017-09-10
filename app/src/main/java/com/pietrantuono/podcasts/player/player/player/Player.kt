package com.pietrantuono.podcasts.player.player.player

import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.PodcastFeedSource

interface Player {
    fun playFeed(source: PodcastFeedSource)
    fun playEpisode(episode: MediaSource)
    fun setEpisode(episode: Episode)
    fun pause()
    fun play()
}