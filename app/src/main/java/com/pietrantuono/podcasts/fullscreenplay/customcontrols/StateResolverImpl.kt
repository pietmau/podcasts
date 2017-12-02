package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import models.pojos.Episode


class StateResolver {
    var episode: Episode? = null
    private val currentlyPlayingMediaId
        get() = supportMediaController?.metadata?.getString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
    private var supportMediaController: MediaControllerCompat? = null

    fun updatePlaybackState(state: PlaybackStateCompat?, presenter: CustomControlsPresenter) {
        when (state?.state) {
            STATE_PLAYING -> onStatePlaying(presenter)
            STATE_PAUSED -> onStatePaused(presenter)
            STATE_NONE, STATE_STOPPED -> presenter?.onStateNone()
            STATE_BUFFERING -> onStateBuffering(presenter)
            STATE_ERROR -> presenter.onError(state)
        }
    }

    private fun onStateBuffering(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter?.onStateBuffering()
        }
    }

    private fun onStatePaused(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter?.onStatePaused()
        }
    }

    private fun onStatePlaying(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter?.onStatePlaying()
        }
    }

    fun onPausePlayClicked(presenter: CustomControlsPresenter) {
        supportMediaController?.playbackState?.let {
            when (it.state) {
                STATE_PLAYING, STATE_BUFFERING -> onPauseClicked(presenter)
                STATE_PAUSED, STATE_STOPPED -> onPlayClicked(presenter)
                STATE_NONE -> onPlayClicked(presenter)
            }
        }
    }

    private fun onPlayClicked(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter.play()
            return
        }
        startNewPodcast()
    }

    private fun onPauseClicked(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter.pause()
            return
        }
        startNewPodcast()
    }

    private fun startNewPodcast() {
        supportMediaController?.transportControls?.stop()
        supportMediaController?.transportControls?.playFromMediaId(episode?.uri, null)
    }

    fun isPlayingCurrentEpisode(): Boolean {
        if (episode?.uri == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.uri.equals(currentlyPlayingMediaId, true)
    }

    fun setMediaController(supportMediaController: MediaControllerCompat) {
        this.supportMediaController = supportMediaController
    }

    fun willHandleClick(): Boolean = episode?.downloaded == true

}