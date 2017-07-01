package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewTreeObserver
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.application.App


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs), ViewTreeObserver.OnGlobalLayoutListener {
    var cachedHeight: Int = 0

    init {
        player = (context?.applicationContext as App).applicationComponent?.simpleExoPlayer()
        showTimeoutMs = -1
        viewTreeObserver.addOnGlobalLayoutListener(this)
        show()
        player.addListener(object : SimpleExoPlayerEventListener() {
            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                showOrHide()
            }
        })
        showOrHide()
    }

    private fun showOrHide() {
        val playbackState = player.playbackState
        Log.d("Foo","showOrHide")
        if (playbackState != ExoPlayer.STATE_READY) {
            hide()
            Log.d("Foo","hide")
        } else {
            show()
            Log.d("Foo","show")
        }
    }

    override fun onGlobalLayout() {
        cachedHeight = height
        if (cachedHeight > 0) {
            viewTreeObserver.removeOnGlobalLayoutListener(this)
            showOrHide()
        }
    }


}