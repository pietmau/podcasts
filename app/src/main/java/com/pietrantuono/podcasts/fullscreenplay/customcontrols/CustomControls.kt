package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat

interface CustomControls {
    fun onError(state: PlaybackStateCompat)
    fun stopSeekbarUpdate()
    fun scheduleSeekbarUpdate()
    fun setStartText(text: String?)
    fun onStatePaused()
    fun onStateNone()
    fun onStatePlaying()
    fun onStateBuffering()
    fun updateMediaDescription(description: MediaDescriptionCompat?)
    fun updateDuration(metadataCompat: MediaMetadataCompat?)
    fun updateProgress()
    fun setPlaybackState(playbackStateCompat: PlaybackStateCompat)
    fun onMetadataChanged(mediaMetadataCompat: MediaMetadataCompat?)
}