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
        private val mediaBrowser: MediaBrowserCompatWrapper
) : SeekBar.OnSeekBarChangeListener, MediaControllerCompat.Callback() {

    private var episode: Episode? = null
    private var state: PlaybackStateCompat? = null

    fun bindView(customControls: CustomControls) {
        viewUpdater.view = customControls
        mediaBrowser.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                connectToSession(mediaBrowser.token)
            }
        }, this)
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        stateResolver.callback = this
        updateProgress()
        if (mediaBrowser.isPlayingOrBuffering()) {
            scheduleSeekbarUpdate()
        }
    }

    fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        executorService.scheduleAtFixedRate { updateProgress() }
    }

    fun updateProgress() {
        if (stateResolver.isPlayingCurrentEpisode()) {
            viewUpdater.setProgress(state)
        }
    }

    fun stopSeekbarUpdate() {
        executorService.cancel(false)
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        this.state = state
        stateResolver.updatePlaybackState(state)
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
        stateResolver.episode = episode
    }

    fun onPlayClicked() {
        if (stateResolver.willHandleClick()) {
            stateResolver.onPausePlayClicked()
            return
        }
        episode?.uri?.let {
            downloader.downloadAndPlayFromUri(it)
            viewUpdater.snack(stateResolver.episode?.title)
        }
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
        if (stateResolver.isPlayingCurrentEpisode()) {
            viewUpdater.onCurrentEpisodeMetadataChanged(metadata)
            return
        }
        viewUpdater.onStatePaused()
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }

    fun startNewPodcast(uri: String?) {
        mediaBrowser?.stop()
        mediaBrowser?.playFromMediaId(uri)

    }

    fun getPlaybackState(): PlaybackStateCompat? {
        return mediaBrowser?.playbackState
    }

    fun getCurrentlyPlayingMediaId(): String? {
        return mediaBrowser?.metadata?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
    }

    fun unbindView() {
        viewUpdater.view = null
    }

}

