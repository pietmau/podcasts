package com.pietrantuono.podcasts.player.player.playback

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlayer
import com.pietrantuono.podcasts.apis.Episode

interface PlaybackWrapper {
    val playbackState: PlaybackStateCompat
    var episode: Episode?
    val media: MediaDescriptionCompat?
    fun pause()
    fun play()
    fun addListener(listener: ExoPlayer.EventListener)
    fun removeListener(listener: ExoPlayer.EventListener)
}