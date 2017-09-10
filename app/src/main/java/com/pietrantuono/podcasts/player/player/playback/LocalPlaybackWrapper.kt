package com.pietrantuono.podcasts.player.player.playback

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlayer
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.MediaSourceCreator


class LocalPlaybackWrapper(private val localPlayback: Playback, private val mediaCreator: MediaSourceCreator) : PlaybackWrapper {
    override val playbackState: PlaybackStateCompat
        get() = localPlayback.playbackState
    override var episode: Episode? = null
        set(value) {
            field = value
            if (value != null) {
                mediaCreator.getMediaSourceFromSingleEpisode(value)?.let { localPlayback.setMediaSource(it) }
            }
        }

    override val media: MediaDescriptionCompat?
        get() = mediaCreator.getMediaDescriptionFromSingleEpisode(episode)

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

