package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import com.pietrantuono.podcasts.R


class CustomControlsPresenter(context: Context) {
    private var view: CustomControls? = null
    private val pauseDrawable: Drawable
    private val playDrawable: Drawable

    init {
        pauseDrawable = ContextCompat.getDrawable(context, R.drawable.uamp_ic_pause_white_48dp)
        playDrawable = ContextCompat.getDrawable(context, R.drawable.uamp_ic_play_arrow_white_48dp)
    }

    fun bindView(customControls: CustomControls) {
        this.view = customControls
    }

    fun updatePlaybackState(state: PlaybackStateCompat) {
        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> {
                onStatePlaying()
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                onStatePaused()
            }
            PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                onStateNone()
            }
            PlaybackStateCompat.STATE_BUFFERING -> {
                onStateBuffering()
            }
            PlaybackStateCompat.STATE_ERROR -> view?.onError(state)
        }
    }

    private fun onStateBuffering() {
        view?.playPause?.visibility = View.INVISIBLE
        view?.loading?.visibility = View.VISIBLE
        view?.line3?.setText(R.string.loading)
        view?.stopSeekbarUpdate()
    }

    private fun onStatePaused() {
        view?.controllers?.visibility = View.VISIBLE
        onStateNone()
    }

    private fun onStateNone() {
        view?.loading?.visibility = View.INVISIBLE
        view?.playPause?.visibility = View.VISIBLE
        view?.playPause?.setImageDrawable(playDrawable)
        view?.stopSeekbarUpdate()
    }

    private fun onStatePlaying() {
        view?.loading?.visibility = View.INVISIBLE
        view?.playPause?.visibility = View.VISIBLE
        view?.playPause?.setImageDrawable(pauseDrawable)
        view?.controllers?.visibility = View.VISIBLE
        view?.scheduleSeekbarUpdate()
    }

}