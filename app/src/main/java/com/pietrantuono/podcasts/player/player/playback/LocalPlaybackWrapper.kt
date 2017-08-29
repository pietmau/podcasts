package com.pietrantuono.podcasts.player.player.playback

import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.service.Player


class LocalPlaybackWrapper(private val localPlayback: Playback, private val mediaCreator: MediaSourceCreator) : Player {

    companion object {
        const val TAG: String = "LocalPlaybackWrapper"
    }

    override fun playFeed(source: PodcastFeedSource) {

    }

    override fun playEpisode(episode: MediaSource) {

    }

    override fun setEpisode(episode: Episode) {
        mediaCreator.getMediaSourceFromSingleEpisode(episode)?.let { localPlayback.setMediaSource(it) }
    }
}