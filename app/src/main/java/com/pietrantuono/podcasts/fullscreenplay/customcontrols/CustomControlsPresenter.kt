package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.widget.SeekBar
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.service.MusicService


class CustomControlsPresenter(
        private val context: Context,
        private val stateResolver: StateResolver,
        private val debugLogger: DebugLogger
)
    : SeekBar.OnSeekBarChangeListener, MediaControllerCompat.Callback() {
    private var view: CustomControls? = null
    private var episode: Episode? = null
    private val transportControls: MediaControllerCompat.TransportControls?
        get() = supportMediaController?.transportControls
    private var mediaBrowser: MediaBrowserCompat? = null
    private var supportMediaController: MediaControllerCompat? = null
    private val TAG: String? = "CustomControlsPresenter"

    private val mediaBrowserCompatConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            try {
                connectToSession(mediaBrowser?.sessionToken)
            } catch (e: RemoteException) {
            }
        }
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        debugLogger.debug(TAG, "connectToSession")
        supportMediaController = MediaControllerCompat(context, token)
        stateResolver.setMediaController(supportMediaController!!)
        supportMediaController?.registerCallback(this)
        val state = supportMediaController?.playbackState
        updatePlaybackState(state)
        view?.onMetadataChanged(supportMediaController?.metadata)
        view?.updateProgress()
        if (state?.state == PlaybackStateCompat.STATE_PLAYING || state?.state == PlaybackStateCompat.STATE_BUFFERING) {
            view?.scheduleSeekbarUpdate()
        }
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
        updatePlaybackState(state)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        view?.onMetadataChanged(metadata)
    }

    fun bindView(customControls: CustomControls) {
        this.view = customControls
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), mediaBrowserCompatConnectionCallback, null)
    }

    fun updatePlaybackState(state: PlaybackStateCompat?) {
        state?.let {
            view?.setPlaybackState(it)
            stateResolver.updatePlaybackState(state, this)
        }
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
        debugLogger.debug(TAG, "setEpisode")
        this.episode = episode
        stateResolver.setEpisode(episode)
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

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun onStop() {
        mediaBrowser?.disconnect()
        supportMediaController?.unregisterCallback(this)
    }
}

