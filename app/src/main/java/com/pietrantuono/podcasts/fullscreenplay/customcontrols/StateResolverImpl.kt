package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import pojos.Episode


class StateResolver {
    private var episode: Episode? = null
    private val currentlyPlayingMediaId
        get() = supportMediaController?.metadata?.getString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
    private var supportMediaController: MediaControllerCompat? = null

    fun updatePlaybackState(state: PlaybackStateCompat, presenter: CustomControlsPresenter) {
        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> {
                onStatePlaying(presenter)
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                onStatePaused(presenter)
            }
            PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                onStateNone(presenter)
            }
            PlaybackStateCompat.STATE_BUFFERING -> {
                onStateBuffering(presenter)
            }
            PlaybackStateCompat.STATE_ERROR -> {
                onError(presenter, state)
            }
        }
    }

    private fun onError(presenter: CustomControlsPresenter, state: PlaybackStateCompat) {
        presenter.onError(state)
    }

    private fun onStateBuffering(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter?.onStateBuffering()
        }
    }

    private fun onStateNone(presenter: CustomControlsPresenter) {
        presenter?.onStateNone()
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
                PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.STATE_BUFFERING -> {
                    onPauseClicked(presenter)
                }
                PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED -> {
                    onPlayClicked(presenter)
                }
                PlaybackStateCompat.STATE_NONE -> {
                    onPlayClicked(presenter)
                }
            }
        }
    }

    private fun onPlayClicked(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter.play()
        } else {
            startNewPodcast()
        }
    }

    private fun onPauseClicked(presenter: CustomControlsPresenter) {
        if (isPlayingCurrentEpisode()) {
            presenter.pause()
        } else {
            startNewPodcast()
        }
    }

    private fun startNewPodcast() {
        val transportControls = supportMediaController?.transportControls
        transportControls?.stop()
        transportControls?.playFromMediaId(episode?.link, null)
    }

    fun isPlayingCurrentEpisode(): Boolean {
        if (episode == null || episode?.link == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.link.equals(currentlyPlayingMediaId, true)
    }

    fun setEpisode(episode: Episode?) {
        this.episode = episode
    }

    fun setMediaController(supportMediaController: MediaControllerCompat) {
        this.supportMediaController = supportMediaController
    }
}