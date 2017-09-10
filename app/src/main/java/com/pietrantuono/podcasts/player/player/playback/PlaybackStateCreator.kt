package com.pietrantuono.podcasts.player.player.playback

import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer

class PlaybackStateCreator {

    fun getPlaybackState(exoPlayer: SimpleExoPlayer?): PlaybackStateCompat {
        val builder = setState(PlaybackStateCompat.Builder(), exoPlayer)
        return builder.build()
    }

    private fun setState(builder: PlaybackStateCompat.Builder, exoPlayer: SimpleExoPlayer?): PlaybackStateCompat.Builder {
        builder.setState(getState(exoPlayer), getPosition(exoPlayer), getSpeed(exoPlayer))
        return builder
    }

    private fun getSpeed(exoPlayer: SimpleExoPlayer?): Float = if (exoPlayer?.playbackParameters == null) 0f else exoPlayer.playbackParameters.speed

    private fun getPosition(exoPlayer: SimpleExoPlayer?): Long = if (exoPlayer == null) 0 else exoPlayer.currentPosition

    private fun getState(exoPlayer: SimpleExoPlayer?): Int = if (exoPlayer == null) ExoPlayer.STATE_IDLE else exoPlayer.playbackState

}