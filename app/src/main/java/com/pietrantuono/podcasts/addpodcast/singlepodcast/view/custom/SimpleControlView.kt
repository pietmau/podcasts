package com.pietrantuono.podcasts.addpodcast.singlepodcast.view.custom

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.player.player.SimpleEventListener


class SimpleControlView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs) {

    private val listener: SimpleEventListener = object : SimpleEventListener() {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_IDLE -> hide()
                ExoPlayer.STATE_BUFFERING -> show()
                ExoPlayer.STATE_ENDED -> hide()
                ExoPlayer.STATE_READY -> show()
            }
        }
    }

    init {
        showTimeoutMs = 0
    }

    fun setCallback() {
        player = (((context as Activity).application as App).applicationComponent!!).simpleExoPlayer()
        listener.onPlayerStateChanged(player.playWhenReady, player.playbackState)
        player.addListener(listener)
    }

    fun removeCallback() {
        player.removeListener(listener)
        player = null
    }

}