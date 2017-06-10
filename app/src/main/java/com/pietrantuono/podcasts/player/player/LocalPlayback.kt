/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pietrantuono.podcasts.player.player

import android.content.Context
import android.media.AudioManager
import android.net.wifi.WifiManager
import android.support.v4.media.session.MediaSessionCompat.QueueItem
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class LocalPlayback(context: Context) : Playback {
    private val context: Context
    private val wifiLock: WifiManager.WifiLock
    private var playOnFocusGain: Boolean = false
    var mCallback: Playback.Callback? = null
    override var currentMediaId: String? = null

    private var currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
    private val audioManager: AudioManager
    private var exoPlayer: SimpleExoPlayer? = null
    private val eventListener = ExoPlayerEventListener()
    private var exoPlayerNullIsStopped = false

    init {
        val applicationContext = context.applicationContext
        this.context = applicationContext

        this.audioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        this.wifiLock = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "uAmp_lock")
    }

    override fun start() {
    }

    override fun stop(notifyListeners: Boolean) {
        giveUpAudioFocus()
        releaseResources(true)
    }

    override
    var state: Int
        get() {
            if (exoPlayer == null) {
                return if (exoPlayerNullIsStopped)
                    PlaybackStateCompat.STATE_STOPPED
                else
                    PlaybackStateCompat.STATE_NONE
            }
            when (exoPlayer!!.playbackState) {
                ExoPlayer.STATE_IDLE -> return PlaybackStateCompat.STATE_PAUSED
                ExoPlayer.STATE_BUFFERING -> return PlaybackStateCompat.STATE_BUFFERING
                ExoPlayer.STATE_READY -> return if (exoPlayer!!.playWhenReady)
                    PlaybackStateCompat.STATE_PLAYING
                else
                    PlaybackStateCompat.STATE_PAUSED
                ExoPlayer.STATE_ENDED -> return PlaybackStateCompat.STATE_PAUSED
                else -> return PlaybackStateCompat.STATE_NONE
            }
        }
        set(state) {}

    override val isConnected: Boolean
        get() = true

    override val isPlaying: Boolean
        get() = playOnFocusGain || exoPlayer != null && exoPlayer!!.playWhenReady

    override val currentStreamPosition: Long
        get() = if (exoPlayer != null) exoPlayer!!.currentPosition else 0

    override fun updateLastKnownStreamPosition() {}

    override fun play(item: QueueItem?) {
        //playMediaSource(item)
    }

    private fun playMediaSource(mediaSource: MediaSource) {
        playOnFocusGain = true
        tryToGetAudioFocus()

        if (exoPlayer == null) {
            exoPlayer = ExoPlayerFactory.newSimpleInstance(
                    context, DefaultTrackSelector(), DefaultLoadControl())
            exoPlayer!!.addListener(eventListener)
        }

        exoPlayer!!.audioStreamType = AudioManager.STREAM_MUSIC

        val dataSourceFactory = DefaultDataSourceFactory(
                context, Util.getUserAgent(context, "uamp"), null)
        val extractorsFactory = DefaultExtractorsFactory()
//        val mediaSource = ExtractorMediaSource(
//                Uri.parse("http://content.blubrry.com/aa_beyond_belief/Episode_58_The_" + "12_Step_Philosophy_of_Alcoholics_Anonymous.mp3"),
//                dataSourceFactory, extractorsFactory, null, null)

        exoPlayer!!.prepare(mediaSource)

        wifiLock.acquire()
        configurePlayerState()
    }

    override fun pause() {
        if (exoPlayer != null) {
            exoPlayer!!.playWhenReady = false
        }
        releaseResources(false)
    }

    override fun seekTo(position: Long) {
        if (exoPlayer != null) {
            exoPlayer!!.seekTo(position)
        }
    }

    override fun setCallback(callback: Playback.Callback?) {
        this.mCallback = callback
    }

    private fun tryToGetAudioFocus() {
        val result = audioManager.requestAudioFocus(
                mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN)
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioFocusState = AUDIO_FOCUSED
        } else {
            currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    private fun giveUpAudioFocus() {
        if (audioManager.abandonAudioFocus(mOnAudioFocusChangeListener) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    private fun configurePlayerState() {
        if (currentAudioFocusState == AUDIO_NO_FOCUS_NO_DUCK) {
            pause()
        } else {

            if (currentAudioFocusState == AUDIO_NO_FOCUS_CAN_DUCK) {
                exoPlayer!!.volume = VOLUME_DUCK
            } else {
                exoPlayer!!.volume = VOLUME_NORMAL
            }
            if (playOnFocusGain) {
                exoPlayer!!.playWhenReady = true
                playOnFocusGain = false
            }
        }
    }

    private val mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> currentAudioFocusState = AUDIO_FOCUSED
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                currentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
                playOnFocusGain = exoPlayer != null && exoPlayer!!.playWhenReady
            }
            AudioManager.AUDIOFOCUS_LOSS ->
                currentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }

        if (exoPlayer != null) {
            configurePlayerState()
        }
    }

    private fun releaseResources(releasePlayer: Boolean) {
        if (releasePlayer && exoPlayer != null) {
            exoPlayer!!.release()
            exoPlayer!!.removeListener(eventListener)
            exoPlayer = null
            exoPlayerNullIsStopped = true
            playOnFocusGain = false
        }

        if (wifiLock.isHeld) {
            wifiLock.release()
        }
    }

    private inner class ExoPlayerEventListener : ExoPlayer.EventListener {
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
        }

        override fun onTracksChanged(
                trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        }

        override fun onLoadingChanged(isLoading: Boolean) {
        }

        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_IDLE, ExoPlayer.STATE_BUFFERING, ExoPlayer.STATE_READY -> if (mCallback != null) {
                    mCallback!!.onPlaybackStatusChanged(state)
                }
                ExoPlayer.STATE_ENDED ->
                    // The media player finished playing the current song.
                    if (mCallback != null) {
                        mCallback!!.onCompletion()
                    }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            var what: String? = null
            if (error != null) {
                when (error.type) {
                    ExoPlaybackException.TYPE_SOURCE -> what = error.sourceException.message
                    ExoPlaybackException.TYPE_RENDERER -> what = error.rendererException.message
                    ExoPlaybackException.TYPE_UNEXPECTED -> what = error.unexpectedException.message
                    else -> what = "Unknown: " + error
                }
            }

            if (mCallback != null) {
                mCallback!!.onError("ExoPlayer error " + what)
            }
        }

        override fun onPositionDiscontinuity() {
        }
    }

    override fun playAll(mediaSource: MediaSource) {
        playMediaSource(mediaSource)
    }

    companion object {
        val VOLUME_DUCK = 0.2f
        val VOLUME_NORMAL = 1.0f
        private val AUDIO_NO_FOCUS_NO_DUCK = 0
        private val AUDIO_NO_FOCUS_CAN_DUCK = 1
        private val AUDIO_FOCUSED = 2
    }
}
