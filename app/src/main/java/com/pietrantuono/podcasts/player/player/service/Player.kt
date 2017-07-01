package com.pietrantuono.podcasts.player.player.service

import com.pietrantuono.podcasts.player.player.PodcastFeedSource

interface Player {

    fun playFeed(source: PodcastFeedSource)
    
}