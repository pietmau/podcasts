package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.application.App


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs) {

    init {
        player = (context?.applicationContext as App).applicationComponent?.simpleExoPlayer()
        showTimeoutMs = -1
        show()
        player.addListener(object : SimpleExoPlayerEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                showOrHide()
            }
        })
    }

    fun showOrHide() {
        val playbackState = player.playbackState
        if (playbackState != ExoPlayer.STATE_READY) {
            hide()
        } else {
            show()
        }
    }

    fun addOnGlobalLayoutListener(onGlobalLayoutListener: () -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun removeOnGlobalLayoutListener(onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener) {
        viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener)
    }

}