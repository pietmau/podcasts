package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.widget.SeekBar
import player.MusicService

class MediaBrowserCompatWrapper(private val context: Context) {
    var callback: MediaControllerCompat.Callback? = null
    var mediaBrowser: MediaBrowserCompat? = null
    var supportMediaController: MediaControllerCompat? = null

    fun init(connectionCallback: MediaBrowserCompat.ConnectionCallback) {
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    this@MediaBrowserCompatWrapper.onConnected()
                    connectionCallback.onConnected()
                } catch (e: RemoteException) {
                }
            }

        }, null)
    }

    private fun onConnected() {
        supportMediaController = MediaControllerCompat(context, mediaBrowser?.sessionToken)
    }

    fun registerCallback(callback: MediaControllerCompat.Callback) {
        this.callback = callback
        supportMediaController?.registerCallback(callback)
    }

    val playbackState: PlaybackStateCompat?
        get() = supportMediaController?.playbackState
    val metadata: MediaMetadataCompat?
        get() = supportMediaController?.metadata
    val state: Int?
        get() = supportMediaController?.playbackState?.state

    fun isPlayingOrBuffering(): Boolean = state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_BUFFERING

    fun play() {
        supportMediaController?.transportControls?.play()
    }

    fun pause() {
        supportMediaController?.transportControls?.pause()
    }

    fun skipToNext() {
        supportMediaController?.transportControls?.skipToNext()
    }

    fun skipToPrevious() {
        supportMediaController?.transportControls?.skipToPrevious()
    }

    fun onStopTrackingTouch(seekBar: SeekBar?) {
        supportMediaController?.transportControls?.seekTo(seekBar!!.progress.toLong())
    }

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun onStop() {
        mediaBrowser?.disconnect()
        if (callback != null) {
            supportMediaController?.unregisterCallback(callback)
        }
    }

}