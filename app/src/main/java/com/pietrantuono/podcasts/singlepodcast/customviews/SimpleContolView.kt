package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlaybackControlView
import com.pietrantuono.podcasts.application.App


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs) {

    init {
        val applicationComponent = (context?.applicationContext as App).applicationComponent
        player = applicationComponent?.simpleExoPlayer()
    }

    fun makeSureisShowing() {
        showTimeoutMs = -1
        show()
    }


}