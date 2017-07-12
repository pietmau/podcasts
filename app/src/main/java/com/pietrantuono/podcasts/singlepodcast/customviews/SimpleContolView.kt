package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
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
        Log.d("foo", "" + playbackState)
        if (playbackState == ExoPlayer.STATE_IDLE) {
            hide()
        } else {
            show()
        }
    }

    fun addOnGlobalLayoutListener(onGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener) {
        viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    fun waitForLayout(function: (Int) -> Unit) {
        addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                val cachedHeight = height
                if (cachedHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    showOrHide()
                    function(cachedHeight)
                }
            }
        })
    }

}