package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.view.View
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.Episode


class CustomControlsPresenter(context: Context) {
    private var view: CustomControls? = null
    private val pauseDrawable: Drawable
    private val playDrawable: Drawable
    private var episode: Episode? = null
    private var supportMediaController: MediaControllerCompat? = null
    private val currentlyPlayingMediaId
        get() = supportMediaController?.metadata?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)

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
            PlaybackStateCompat.STATE_ERROR -> onError(state)
        }
    }

    private fun onError(state: PlaybackStateCompat) {
        view?.onError(state)
    }

    private fun onStateBuffering() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.playPause?.visibility = View.INVISIBLE
        view?.loading?.visibility = View.VISIBLE
        view?.line3?.setText(R.string.loading)
        view?.stopSeekbarUpdate()
    }

    private fun isPlayingCurrentEpisode(): Boolean {
        if (episode == null || episode?.link == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.link.equals(currentlyPlayingMediaId, true)
    }

    private fun onStatePaused() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.controllers?.visibility = View.VISIBLE
        onStateNone()
    }

    private fun onStateNone() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.loading?.visibility = View.INVISIBLE
        view?.playPause?.visibility = View.VISIBLE
        view?.playPause?.setImageDrawable(playDrawable)
        view?.stopSeekbarUpdate()
    }

    private fun onStatePlaying() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.loading?.visibility = View.INVISIBLE
        view?.playPause?.visibility = View.VISIBLE
        view?.playPause?.setImageDrawable(pauseDrawable)
        view?.controllers?.visibility = View.VISIBLE
        view?.scheduleSeekbarUpdate()
    }

    fun setEpisode(episode: Episode?) {
        this.episode = episode
    }

    fun setMediaController(supportMediaController: MediaControllerCompat) {
        this.supportMediaController = supportMediaController
    }
}