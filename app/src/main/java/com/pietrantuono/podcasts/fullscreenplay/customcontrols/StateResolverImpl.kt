package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import models.pojos.Episode


class StatusManager {
    var episode: Episode? = null
    var callback: CustomControlsPresenter? = null
    private var config: CustomControls.Configuration? = null

    fun updatePlaybackState(state: PlaybackStateCompat?) {
        when (state?.state) {
            STATE_PLAYING -> onStatePlaying()
            STATE_PAUSED -> onStatePaused()
            STATE_NONE, STATE_STOPPED -> onStateNone()
            STATE_BUFFERING -> onStateBuffering()
            STATE_ERROR -> onError(state)
        }
    }

    private fun onError(state: PlaybackStateCompat) {
        callback?.onError(state)
    }

    private fun onStateNone() {
        callback?.onStateNone()
    }

    private fun onStateBuffering() {
        if (isPlayingCurrentEpisode()) {
            callback?.onStateBuffering()
        }
    }

    private fun onStatePaused() {
        if (isPlayingCurrentEpisode()) {
            callback?.onStatePaused()
        }
    }

    private fun onStatePlaying() {
        if (isPlayingCurrentEpisode()) {
            callback?.onStatePlaying()
        }
    }

    fun onPausePlayClicked() {
        if (dontNeedToDownload()) {
            callback?.getPlaybackState()?.state?.let {
                when (it) {
                    STATE_PLAYING, STATE_BUFFERING -> onPauseClicked()
                    STATE_PAUSED, STATE_STOPPED -> onPlayClicked()
                    STATE_NONE -> onPlayClicked()
                }
            }
        } else {
            episode?.uri?.let {
                callback?.sendCustomActionDownloadAndPlay(it)
            }
        }
    }

    private fun onPlayClicked() {
        if (isPlayingCurrentEpisode()) {
            callback?.play()
            return
        }
        startNewPodcast()
    }

    private fun onPauseClicked() {
        if (isPlayingCurrentEpisode()) {
            callback?.pause()
            return
        }
        startNewPodcast()
    }

    private fun startNewPodcast() {
        callback?.startNewPodcast(episode?.uri)
    }

    fun isPlayingCurrentEpisode(): Boolean {
        val currentlyPlayingMediaId = callback?.getCurrentlyPlayingMediaId()
        if (episode?.uri == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.uri.equals(currentlyPlayingMediaId, true)
    }

    fun dontNeedToDownload(): Boolean =
            if (episode?.downloaded == true) {
                true
            } else {
                config?.shouldStreamAudio == true
            }

    fun setConfiguration(config: CustomControls.Configuration) {
        this.config = config
    }
}