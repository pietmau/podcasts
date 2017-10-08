package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.widget.SeekBar
import com.pietrantuono.podcasts.apis.Episode


class CustomControlsPresenter(private val stateResolver: StateResolver) : SeekBar.OnSeekBarChangeListener {
    private var view: CustomControls? = null
    private var episode: Episode? = null
    private var supportMediaController: MediaControllerCompat? = null
    private val currentlyPlayingMediaId
        get() = supportMediaController?.metadata?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
    private val transportControls
        get() = supportMediaController?.transportControls

    fun bindView(customControls: CustomControls) {
        this.view = customControls
    }

    fun updatePlaybackState(state: PlaybackStateCompat) {
        stateResolver.updatePlaybackState(state, this)
    }

    fun onError(state: PlaybackStateCompat) {
        view?.onError(state)
    }

    fun onStateBuffering() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.onStateBuffering()
    }

    private fun isPlayingCurrentEpisode(): Boolean {
        if (episode == null || episode?.link == null || currentlyPlayingMediaId == null) {
            return false
        }
        return episode?.link.equals(currentlyPlayingMediaId, true)
    }

    fun onStatePaused() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.onStatePaused()
    }

    fun onStateNone() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.onStateNone()
    }

    fun onStatePlaying() {
        if (!isPlayingCurrentEpisode()) {
            return
        }
        view?.onStatePlaying()
    }

    fun setEpisode(episode: Episode?) {
        this.episode = episode
    }

    fun setMediaController(supportMediaController: MediaControllerCompat) {
        this.supportMediaController = supportMediaController
    }

    fun onPlayClicked() {
        supportMediaController?.playbackState?.let {
            stateResolver.onPlayClicked(it, this)
        }
    }

    fun play(): Unit? {
        transportControls?.play()
        return view?.scheduleSeekbarUpdate()
    }

    fun pause(): Unit? {
        transportControls?.pause()
        return view?.stopSeekbarUpdate()
    }

    fun skipToNext() {
        transportControls?.skipToNext()
    }

    fun skipToPrevious() {
        transportControls?.skipToPrevious()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        view?.setStartText(DateUtils.formatElapsedTime((progress / 1000).toLong()))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        view?.stopSeekbarUpdate()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        transportControls?.seekTo(seekBar!!.progress.toLong())
        view?.scheduleSeekbarUpdate()
    }
}

