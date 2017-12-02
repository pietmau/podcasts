package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.os.SystemClock
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v4.media.session.PlaybackStateCompat.STATE_BUFFERING
import android.support.v4.media.session.PlaybackStateCompat.STATE_PLAYING
import android.text.format.DateUtils
import android.widget.SeekBar
import com.pietrantuono.podcasts.downloader.downloader.Downloader
import models.pojos.Episode
import player.MusicService

class CustomControlsPresenter(
        private val context: Context,
        private val stateResolver: StateResolver,
        private val executorService: SimpleExecutor,
        private val downloader: Downloader
) : SeekBar.OnSeekBarChangeListener, MediaControllerCompat.Callback() {

    private var episode: Episode? = null
    private var view: CustomControls? = null
    private var mediaBrowser: MediaBrowserCompat? = null
    private var supportMediaController: MediaControllerCompat? = null
    private var lastPlaybackState: PlaybackStateCompat? = null

    fun bindView(customControls: CustomControls) {
        this.view = customControls
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    connectToSession(mediaBrowser?.sessionToken)
                } catch (e: RemoteException) {
                }
            }
        }, null)
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        supportMediaController = MediaControllerCompat(context, token)
        stateResolver.setMediaController(supportMediaController!!)
        supportMediaController?.registerCallback(this)
        val state = supportMediaController?.playbackState
        onPlaybackStateChanged(state)
        onMetadataChanged(supportMediaController?.metadata)
        updateProgress()
        if (state?.state == STATE_PLAYING || state?.state == STATE_BUFFERING) {
            scheduleSeekbarUpdate()
        }
    }

    fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        executorService.scheduleAtFixedRate { updateProgress() }
    }

    fun updateProgress() {
        if (!stateResolver.isPlayingCurrentEpisode()) {
            return
        }
        lastPlaybackState?.let {
            var currentPosition = it.position
            if (it.state == STATE_PLAYING) {
                currentPosition += ((SystemClock.elapsedRealtime() - it.lastPositionUpdateTime).toInt() * it.playbackSpeed).toLong()
            }
            view?.setProgress(currentPosition.toInt())
        }
    }

    fun stopSeekbarUpdate() {
        executorService.cancel(false)
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
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
        if (episode?.downloaded == true) {
            stateResolver.onPausePlayClicked(this)
            return
        }
        episode?.uri?.let {
            downloader.downloadAndPlayFromUri(it)
            view?.snack(episode?.title + " will be downloaded and added to the playlist")
        }
    }

    fun play() {
        supportMediaController?.transportControls?.play()
        scheduleSeekbarUpdate()
    }

    fun pause() {
        supportMediaController?.transportControls?.pause()
        stopSeekbarUpdate()
    }

    fun skipToNext() {
        supportMediaController?.transportControls?.skipToNext()
    }

    fun skipToPrevious() {
        supportMediaController?.transportControls?.skipToPrevious()
    }

    override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        view?.setStartText(DateUtils.formatElapsedTime((progress / 1000).toLong()))
    }

    override fun onStartTrackingTouch(seekBar: SeekBar?) {
        stopSeekbarUpdate()
    }

    override fun onStopTrackingTouch(seekBar: SeekBar?) {
        supportMediaController?.transportControls?.seekTo(seekBar!!.progress.toLong())
        scheduleSeekbarUpdate()
    }

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun onStop() {
        mediaBrowser?.disconnect()
        supportMediaController?.unregisterCallback(this)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (stateResolver.isPlayingCurrentEpisode() && metadata != null) {
            metadata.description?.let { view?.updateMediaDescription(it) }
            view?.updateDuration(metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt())
        }
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }
}

