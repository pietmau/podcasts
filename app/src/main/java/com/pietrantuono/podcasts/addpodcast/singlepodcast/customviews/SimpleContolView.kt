package com.pietrantuono.podcasts.addpodcast.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import android.widget.TextView
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.metadata.Metadata
import com.google.android.exoplayer2.metadata.MetadataRenderer
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs) {
    private var tile: TextView

    init {
        player = (context?.applicationContext as App).applicationComponent?.simpleExoPlayer()
        showTimeoutMs = -1
        show()
        player.addListener(object : SimpleExoPlayerEventListener() {

            override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                showOrHide()
            }
        })
        tile = findViewById(R.id.title) as TextView

        (player as SimpleExoPlayer?)?.setMetadataOutput(object : MetadataRenderer.Output {
            override fun onMetadata(metadata: Metadata?) {

            }
        })
    }

    fun showOrHide() {
        val playbackState = player.playbackState
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