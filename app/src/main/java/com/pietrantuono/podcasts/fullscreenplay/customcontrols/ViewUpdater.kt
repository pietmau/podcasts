package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.Context
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import com.pietrantuono.podcasts.R

class ViewUpdater(
        private val context: Context,
        private val positionCalculator: PositionCalculator) {
    lateinit var view: CustomControls

    fun setProgress(playbackStateCompat: PlaybackStateCompat?) {
        if (playbackStateCompat == null) {
            return
        }
        view?.setProgress(positionCalculator.calculatePostion(playbackStateCompat))
    }

    fun onError(state: PlaybackStateCompat) {
        view?.onError(state)
    }

    fun onStateBuffering() {
        view?.onStateBuffering()
    }

    fun onStatePaused() {
        view?.onStatePaused()
    }

    fun onStateNone() {
        view?.onStateNone()
    }

    fun onStatePlaying() {
        view?.onStatePlaying()
    }

    fun snack(message: String?) {
        view?.snack(message + context.getString(R.string.will_be_downloaded))
    }

    fun setStartText(progress: Int) {
        view?.setStartText(DateUtils.formatElapsedTime((progress / 1000).toLong()))
    }

    fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (metadata == null) {
            return
        }
        metadata.description?.let {
            view?.updateMediaDescription(it)
        }
        view?.updateDuration(metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt())
    }

}