package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.SeekBar
import models.pojos.Episode
import player.playback.CustomActionResolver.Companion.CUSTOM_ACTION_DOWNLOAD_AND_ADD_TO_QUEUE
import player.playback.CustomActionResolver.Companion.EXTRA_EPISODE_URI

class CustomControlsPresenter(
        private val statusManager: StatusManager,
        private val executorService: SimpleExecutor,
        private val viewUpdater: ViewUpdater,
        private val mediaBrowser: MediaBrowserCompatWrapper
) : SeekBar.OnSeekBarChangeListener, MediaControllerCompat.Callback() {

    private var episode: Episode? = null
    private var state: PlaybackStateCompat? = null

    fun bindView(customControls: CustomControls) {
        viewUpdater.view = customControls
        mediaBrowser.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                connectToSession()
            }
        }, this)
    }

    @Throws(RemoteException::class)
    private fun connectToSession() {
        statusManager.callback = this
        updateProgress()
        if (mediaBrowser.isPlayingOrBuffering()) {
            scheduleSeekbarUpdate()
        }
    }

    private fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        executorService.scheduleAtFixedRate { updateProgress() }
    }

    private fun updateProgress() {
        if (statusManager.isPlayingCurrentEpisode()) {
            viewUpdater.setProgress(state)
        }
    }

    private fun stopSeekbarUpdate() {
        executorService.cancel(false)
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        this.state = state
        statusManager.updatePlaybackState(state)
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
        this.episode = episode
        statusManager.episode = episode
    }

    fun onPlayClicked() {
        statusManager.onPausePlayClicked()
    }

    fun play() {
        mediaBrowser.play()
        scheduleSeekbarUpdate()
    }

    fun pause() {
        mediaBrowser.pause()
        stopSeekbarUpdate()
    }

    fun skipToNext() {
        mediaBrowser.skipToNext()
    }

    fun skipToPrevious() {
        mediaBrowser.skipToPrevious()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        viewUpdater.setStartText(progress)
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        stopSeekbarUpdate()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        mediaBrowser.onStopTrackingTouch(seekBar)
        scheduleSeekbarUpdate()
    }

    fun onStart() {
        mediaBrowser.onStart()
    }

    fun onStop() {
        mediaBrowser.onStop()
        executorService.cancel(true)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (statusManager.isPlayingCurrentEpisode()) {
            viewUpdater.onCurrentEpisodeMetadataChanged(metadata)
            return
        }
        viewUpdater.onStatePaused()
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }

    fun startNewPodcast() {
        episode?.let {
            mediaBrowser.stop()
            if (it.progress <= 0) {
                playFromMediaId(it.uri)
            } else {
                viewUpdater.askUserRestartOrResume(it.title)
            }
        }
    }

    private fun playFromMediaId(uri: String?) {
        mediaBrowser.playFromMediaId(uri)
    }

    fun getPlaybackState(): Int? {
        return mediaBrowser.playbackState?.state
    }

    fun getCurrentlyPlayingMediaId(): String? {
        return mediaBrowser.metadata?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
    }

    fun unbindView() {
        viewUpdater.view = null
    }

    fun setConfiguration(config: CustomControls.Configuration) {
        statusManager.setConfiguration(config)
    }

    fun sendCustomActionDownloadAndPlay(uri: String) {
        val bundle = Bundle()
        bundle.putString(EXTRA_EPISODE_URI, uri)
        mediaBrowser.sendCustomAction(CUSTOM_ACTION_DOWNLOAD_AND_ADD_TO_QUEUE, bundle, null)
        viewUpdater.snack(statusManager.episode?.title)
    }

    fun restart() {
        playFromMediaId(episode?.uri)
    }

    fun resume() {
        episode?.let {
            playFromMediaId(it.uri)
            mediaBrowser.seekTo(it.progress)
        }
    }

}

