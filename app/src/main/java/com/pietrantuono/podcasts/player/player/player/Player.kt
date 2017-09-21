package com.pietrantuono.podcasts.player.player.player

import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode

interface Player {
    fun playEpisode(episode: MediaSource)
    fun setEpisode(episode: Episode)
    fun pause()
    fun play()
    fun stop()
}