package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

interface CustomControls {
    var playPause: ImageView
    var loading: ProgressBar
    var line3: TextView
    var controllers: View
    fun onError(state: PlaybackStateCompat)
    fun stopSeekbarUpdate()
    fun scheduleSeekbarUpdate()
}