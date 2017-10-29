package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.ComponentName
import android.content.Context
import android.os.Handler
import android.os.RemoteException
import android.os.SystemClock
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
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit


class CustomControlsPresenter(
        private val context: Context,
        private val stateResolver: StateResolver,
        private val debugLogger: DebugLogger)
    : SeekBar.OnSeekBarChangeListener {

    private var view: CustomControls? = null
    private var episode: Episode? = null
    private val transportControls: MediaControllerCompat.TransportControls?
        get() = supportMediaController?.transportControls
    private var mediaBrowser: MediaBrowserCompat? = null
    private var supportMediaController: MediaControllerCompat? = null
    private val TAG: String? = "CustomControlsPresenter"
    private val simpleMediaControllerCompatCallback = SimpleMediaControllerCompatCallback(this)
    private val aHandler = Handler()
    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private var scheduleFuture: ScheduledFuture<*>? = null
    private var lastPlaybackState: PlaybackStateCompat? = null

    companion object {
        private val PROGRESS_UPDATE_INTERNAL: Long = 1000
        private val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 100
    }

    fun bindView(customControls: CustomControls) {
        this.view = customControls
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    mediaBrowser?.sessionToken?.let { connectToSession(it) }
                } catch (e: RemoteException) {
                }
            }
        }, null)
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token) {
        supportMediaController = MediaControllerCompat(context, token)
        stateResolver.setMediaController(supportMediaController!!)
        supportMediaController?.registerCallback(simpleMediaControllerCompatCallback)
        val state = supportMediaController?.playbackState
        updatePlaybackState(state)
        onMetadataChanged(supportMediaController?.metadata)
        updateProgress()
        if (state?.state == PlaybackStateCompat.STATE_PLAYING || state?.state == PlaybackStateCompat.STATE_BUFFERING) {
            scheduleSeekbarUpdate()
        }
    }

    fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        if (!executorService.isShutdown) {
            scheduleFuture = executorService.scheduleAtFixedRate({ aHandler.post({ updateProgress() }) }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
        }
    }

    fun updateProgress() {
        if (!stateResolver.isPlayingCurrentEpisode()) {
            return
        }
        lastPlaybackState?.let {
            var currentPosition = it.position
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                currentPosition += ((SystemClock.elapsedRealtime() - it.lastPositionUpdateTime).toInt() * it.playbackSpeed).toLong()
            }
            view?.setProgress(currentPosition.toInt())
        }
    }

    fun stopSeekbarUpdate() {
        scheduleFuture?.cancel(false)
    }

    fun updatePlaybackState(state: PlaybackStateCompat?) {
        lastPlaybackState = state
        state?.let {
            stateResolver.updatePlaybackState(it, this)
        }
    }

    fun onError(state: PlaybackStateCompat) {
        view?.onError(state)
    }

    fun onStateBuffering() {
        view?.onStateBuffering()
        stopSeekbarUpdate()
    }

    fun onStatePaused() {
        view?.onStatePaused()
        stopSeekbarUpdate()
    }

    fun onStateNone() {
        view?.onStateNone()
        stopSeekbarUpdate()
    }

    fun onStatePlaying() {
        view?.onStatePlaying()
        scheduleSeekbarUpdate()
    }

    fun setEpisode(episode: Episode?) {
        this.episode = episode
        stateResolver.setEpisode(episode)
    }

    fun onPlayClicked() {
        stateResolver.onPausePlayClicked(this)
    }

    fun play() {
        transportControls?.play()
        scheduleSeekbarUpdate()
    }

    fun pause() {
        transportControls?.pause()
        stopSeekbarUpdate()
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
        stopSeekbarUpdate()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        transportControls?.seekTo(seekBar!!.progress.toLong())
        scheduleSeekbarUpdate()
    }

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun onStop() {
        mediaBrowser?.disconnect()
        supportMediaController?.unregisterCallback(simpleMediaControllerCompatCallback)
    }

    fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (!stateResolver.isPlayingCurrentEpisode()) {
            return
        }
        metadata?.let {
            it.description?.let { view?.updateMediaDescription(it) }
            view?.updateDuration(it.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt())
        }
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }
}

