package com.pietrantuono.podcasts.fullscreenplay.custom

import android.content.Context
import android.support.v4.graphics.ColorUtils
import android.util.AttributeSet
import com.google.android.exoplayer2.ui.PlaybackControlView

class ColorizedPlaybackControlView : PlaybackControlView {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        showTimeoutMs = -1
        show()
    }

    fun setBackgroundColors(backgroundColor: Int) {
        setBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, (255 * 8 / 10)))
    }
}