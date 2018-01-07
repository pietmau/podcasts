package com.pietrantuono.podcasts.fullscreenplay.custom

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import com.pietrantuono.podcasts.application.App

class PlaybackControlView : CustomPlaybackControlView {

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        showTimeoutMs = -1
        show()
    }

    fun onStart() {
        player = (((context as Activity).application as App).appComponent!!).simpleExoPlayer()
    }

    fun onStop() {
        player = null
    }

    interface Callback {
        fun onPlayClicked()
        fun onPlayerError(errorMessage: CharSequence?)
    }
}