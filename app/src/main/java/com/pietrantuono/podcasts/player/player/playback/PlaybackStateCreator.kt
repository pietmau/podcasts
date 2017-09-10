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

    private fun getState(exoPlayer: SimpleExoPlayer?): Int = if (exoPlayer == null) PlaybackStateCompat.STATE_NONE else parseState(exoPlayer)

    private fun parseState(exoPlayer: SimpleExoPlayer): Int =
            when (exoPlayer.playWhenReady) {
                true -> playWhenReady(exoPlayer.playbackState)
                false -> notPlayWhenReady(exoPlayer.playbackState)
            }

    private fun notPlayWhenReady(playbackState: Int): Int =
            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> PlaybackStateCompat.STATE_STOPPED
                ExoPlayer.STATE_ENDED -> PlaybackStateCompat.STATE_STOPPED
                ExoPlayer.STATE_READY -> PlaybackStateCompat.STATE_STOPPED
                ExoPlayer.STATE_IDLE -> PlaybackStateCompat.STATE_STOPPED
                else -> PlaybackStateCompat.STATE_NONE
            }

    private fun playWhenReady(playbackState: Int): Int =
            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> PlaybackStateCompat.STATE_BUFFERING
                ExoPlayer.STATE_ENDED -> PlaybackStateCompat.STATE_STOPPED
                ExoPlayer.STATE_READY -> PlaybackStateCompat.STATE_PLAYING
                ExoPlayer.STATE_IDLE -> PlaybackStateCompat.STATE_PAUSED
                else -> PlaybackStateCompat.STATE_NONE
            }

}