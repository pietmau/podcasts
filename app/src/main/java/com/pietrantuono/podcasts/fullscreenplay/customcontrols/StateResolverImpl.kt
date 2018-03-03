package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import models.pojos.Episode


class StatusManager {
    var episode: Episode? = null
    var callback: CustomControlsPresenter? = null
    private var config: CustomControls.Configuration? = null

    fun updatePlaybackState(playbackStateCompat: PlaybackStateCompat?) {
        if (isPlayingCurrentEpisode()) {
            when (playbackStateCompat?.state) {
                STATE_PLAYING -> onStatePlaying()
                STATE_PAUSED -> onStatePaused()
                STATE_BUFFERING -> onStateBuffering()
                STATE_NONE, STATE_STOPPED -> onStateNone()
                STATE_ERROR -> onError(playbackStateCompat)
            }
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
        if (doNotNeedToDownload()) {
            playPauseOrStartNew()
        } else {
            downloadAndPlay()
        }
    }

    private fun playPauseOrStartNew() {
        if (isPlayingCurrentEpisode()) {
            playOrPause()
        } else {
            startNewPodcast()
        }
    }

    private fun playOrPause() {
        callback?.getPlaybackState()?.let {
            when (it) {
                STATE_PLAYING, STATE_BUFFERING -> pause()
                STATE_PAUSED, STATE_STOPPED, STATE_NONE -> play()
            }
        }
    }

    private fun downloadAndPlay() {
        episode?.uri?.let {
            callback?.sendCustomActionDownloadAndPlay(it)
        }
    }

    private fun play() {
        callback?.play()
    }

    private fun pause() {
        callback?.pause()
    }

    private fun startNewPodcast() {
        callback?.startNewPodcast()
    }

    fun isPlayingCurrentEpisode(): Boolean {
        val currentlyPlayingMediaId = callback?.getCurrentlyPlayingMediaId()
        if (episode?.uri == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.uri.equals(currentlyPlayingMediaId, true)
    }

    fun doNotNeedToDownload(): Boolean =
            if (episode?.downloaded == true) {
                true
            } else {
                config?.shouldStreamAudio == true
            }

    fun setConfiguration(config: CustomControls.Configuration) {
        this.config = config
    }
}