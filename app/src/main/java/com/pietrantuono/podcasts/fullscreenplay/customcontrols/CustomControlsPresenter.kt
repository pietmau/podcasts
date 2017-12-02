package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.SeekBar
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import models.pojos.Episode

class CustomControlsPresenter(
        private val stateResolver: StateResolver,
        private val executorService: SimpleExecutor,
        private val downloader: Downloader,
        private val viewUpdater: ViewUpdater,
        private val mediaBrowserCompatWrapper: MediaBrowserCompatWrapper
) : SeekBar.OnSeekBarChangeListener, MediaControllerCompat.Callback() {

    private var lastPlaybackState: PlaybackStateCompat? = null

    fun bindView(customControls: CustomControls) {
        viewUpdater.view = customControls
        mediaBrowserCompatWrapper.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
            }
        })
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        stateResolver.setMediaController(mediaBrowserCompatWrapper.supportMediaController!!)
        mediaBrowserCompatWrapper.registerCallback(this)
        onPlaybackStateChanged(mediaBrowserCompatWrapper?.playbackState)
        onMetadataChanged(mediaBrowserCompatWrapper?.metadata)
        updateProgress()
        if (mediaBrowserCompatWrapper.isPlayingOrBuffering()) {
            scheduleSeekbarUpdate()
        }
    }

    fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        executorService.scheduleAtFixedRate { updateProgress() }
    }

    fun updateProgress() {
        if (stateResolver.isPlayingCurrentEpisode()) {
            viewUpdater.setProgress(lastPlaybackState)
        }
    }

    fun stopSeekbarUpdate() {
        executorService.cancel(false)
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        lastPlaybackState = state
        stateResolver.updatePlaybackState(state, this)
    }

    fun onError(state: PlaybackStateCompat) {
        viewUpdater.onError(state)
    }

    fun onStateBuffering() {
        viewUpdater.onStateBuffering()
        stopSeekbarUpdate()
    }

    fun onStatePaused() {
        viewUpdater.onStatePaused()
        stopSeekbarUpdate()
    }

    fun onStateNone() {
        viewUpdater.onStateNone()
        stopSeekbarUpdate()
    }

    fun onStatePlaying() {
        viewUpdater.onStatePlaying()
        scheduleSeekbarUpdate()
    }

    fun setEpisode(episode: Episode?) {
        stateResolver.episode = episode
    }

    fun onPlayClicked() {
        if (stateResolver.willHandleClick()) {
            stateResolver.onPausePlayClicked(this)
            return
        }
        stateResolver.episode?.uri?.let {
            downloader.downloadAndPlayFromUri(it)
            viewUpdater.snack(stateResolver.episode?.title)
        }
    }

    fun play() {
        mediaBrowserCompatWrapper.play()
        scheduleSeekbarUpdate()
    }

    fun pause() {
        mediaBrowserCompatWrapper.pause()
        stopSeekbarUpdate()
    }

    fun skipToNext() {
        mediaBrowserCompatWrapper.skipToNext()
    }

    fun skipToPrevious() {
        mediaBrowserCompatWrapper.skipToPrevious()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        viewUpdater.setStartText(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        stopSeekbarUpdate()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        mediaBrowserCompatWrapper.onStopTrackingTouch(seekBar)
        scheduleSeekbarUpdate()
    }

    fun onStart() {
        mediaBrowserCompatWrapper.onStart()
    }

    fun onStop() {
        mediaBrowserCompatWrapper.onStop()
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (stateResolver.isPlayingCurrentEpisode()) {
            viewUpdater.onMetadataChanged(metadata)
        }
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }

}

