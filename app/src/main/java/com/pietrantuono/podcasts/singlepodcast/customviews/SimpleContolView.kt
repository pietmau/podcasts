package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.ViewTreeObserver
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.application.App


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs), ViewTreeObserver.OnGlobalLayoutListener {
    var cachedHeight: Int = 0


    init {
        player = (context?.applicationContext as App).applicationComponent?.simpleExoPlayer()
        showTimeoutMs = -1
        show()
        viewTreeObserver.addOnGlobalLayoutListener(this)
    }

    override fun onGlobalLayout() {
        cachedHeight = height
        viewTreeObserver.removeOnGlobalLayoutListener(this)
    }


}