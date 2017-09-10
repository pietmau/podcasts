package com.pietrantuono.podcasts.player.player.playback

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.MediaSourceCreator
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.player.Player


class LocalPlaybackWrapper(private val localPlayback: Playback, private val mediaCreator: MediaSourceCreator) : Player {
    override val playbackState: PlaybackStateCompat
        get() = localPlayback.playbackState
    private var episode: Episode? = null

    override val media: MediaDescriptionCompat?
        get() = mediaCreator.getMediaDescriptionFromSingleEpisode(episode)

    companion object {
        const val TAG: String = "LocalPlaybackWrapper"
    }

    override fun playFeed(source: PodcastFeedSource) {

    }

    override fun playEpisode(episode: MediaSource) {

    }

    override fun setEpisode(episode: Episode) {
        this.episode = episode
        mediaCreator.getMediaSourceFromSingleEpisode(episode)?.let { localPlayback.setMediaSource(it) }
    }

    override fun pause() {
        localPlayback.pause()
    }

    override fun play() {
        localPlayback.play()
    }

    override fun addListener(listener: ExoPlayer.EventListener) {
        localPlayback.addListener(listener)
    }

    override fun removeListener(listener: ExoPlayer.EventListener) {
        localPlayback.removeListener(listener)
    }

}