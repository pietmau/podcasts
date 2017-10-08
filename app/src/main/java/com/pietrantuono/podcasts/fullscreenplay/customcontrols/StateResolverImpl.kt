package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.PlaybackStateCompat

class StateResolver  {

    fun updatePlaybackState(state: PlaybackStateCompat, presenter: CustomControlsPresenter) {
        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> {
                presenter?.onStatePlaying()
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                presenter?.onStatePaused()
            }
            PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                presenter?.onStateNone()
            }
            PlaybackStateCompat.STATE_BUFFERING -> {
                presenter?.onStateBuffering()
            }
            PlaybackStateCompat.STATE_ERROR -> {
                presenter.onError(state)
            }
        }
    }

    fun onPlayClicked(playbackStateCompat: PlaybackStateCompat, presenter: CustomControlsPresenter) {
        when (playbackStateCompat.state) {
            PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.STATE_BUFFERING -> {
                presenter.pause()
            }
            PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED -> {
                presenter.play()
            }
            else -> {
            }
        }
    }
}