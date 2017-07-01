package com.pietrantuono.podcasts.singlepodcast.customviews

import android.content.Context
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlaybackControlView


class SimpleContolView(context: Context?, attrs: AttributeSet?) : PlaybackControlView(context, attrs) {

    fun makeSureisShowing() {
        showTimeoutMs = -1
        show()
    }
}