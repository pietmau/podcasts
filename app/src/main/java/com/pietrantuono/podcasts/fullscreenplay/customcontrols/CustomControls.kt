package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.PlaybackStateCompat

interface CustomControls {
    fun onError(state: PlaybackStateCompat)
    fun setStartText(text: String?)
    fun onStatePaused()
    fun onStateNone()
    fun onStatePlaying()
    fun onStateBuffering()
    fun updateMediaDescription(description: MediaDescriptionCompat)
    fun updateDuration(toInt: Int)
    fun setProgress(progress: Int)
    fun snack(message: String)
    fun setConfiguration(config: Configuration)

    class Configuration(val shouldNotDownload: Boolean)
}