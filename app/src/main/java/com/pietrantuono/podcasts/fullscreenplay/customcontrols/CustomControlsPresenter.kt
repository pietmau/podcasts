package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.widget.SeekBar
import com.pietrantuono.podcasts.apis.Episode


class CustomControlsPresenter(private val stateResolver: StateResolver) : SeekBar.OnSeekBarChangeListener {
    private var view: CustomControls? = null
    private var episode: Episode? = null
    private var transportControls: MediaControllerCompat.TransportControls? = null

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

    fun setEpisode(episode: Episode?) {
        this.episode = episode
        stateResolver.setEpisode(episode)
    }

    fun setMediaController(supportMediaController: MediaControllerCompat) {
        this.transportControls = supportMediaController.transportControls
        stateResolver.setMediaController(supportMediaController)
    }

    fun onPlayClicked() {
        stateResolver.onPausePlayClicked(this)
    }

    fun play() {
        transportControls?.play()
        view?.scheduleSeekbarUpdate()
    }

    fun pause() {
        transportControls?.pause()
        view?.stopSeekbarUpdate()
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

