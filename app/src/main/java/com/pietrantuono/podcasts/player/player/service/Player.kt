package com.pietrantuono.podcasts.player.player.service

import com.google.android.exoplayer2.SimpleExoPlayer
import com.pietrantuono.podcasts.player.player.PodcastFeedSource

interface Player {

    fun playFeed(source: PodcastFeedSource)

    var exoPlayer: SimpleExoPlayer?
}