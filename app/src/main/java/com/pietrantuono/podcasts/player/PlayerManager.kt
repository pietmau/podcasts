package com.pietrantuono.podcasts.player

import com.pietrantuono.podcasts.apis.PodcastFeed

interface PlayerManager {
    fun onStop()
    fun onStart()
    fun listenToAll(podcastFeed: PodcastFeed?)
}
