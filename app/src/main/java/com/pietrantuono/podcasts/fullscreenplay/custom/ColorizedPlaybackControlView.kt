package com.pietrantuono.podcasts.fullscreenplay.custom

import android.app.Activity
import android.content.Context
import android.support.v4.graphics.ColorUtils
import android.util.AttributeSet
import com.pietrantuono.podcasts.application.App

class ColorizedPlaybackControlView : CustomPlaybackControlView {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        showTimeoutMs = -1
        show()
    }

    fun setBackgroundColors(backgroundColor: Int) {
        setBackgroundColor(ColorUtils.setAlphaComponent(backgroundColor, (255 * 8 / 10)))
    }

    fun onStart() {
        player = (((context as Activity).application as App).applicationComponent!!).simpleExoPlayer()
    }

    fun onStop() {
        player = null
    }
}