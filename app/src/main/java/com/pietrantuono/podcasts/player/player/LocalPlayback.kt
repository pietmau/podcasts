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
import android.content.IntentFilter
import android.media.AudioManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.support.v4.media.session.MediaSessionCompat.QueueItem
import android.support.v4.media.session.PlaybackStateCompat
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

/**
 * A class that implements local media playback using [ ]
 */
class LocalPlayback(context: Context) : Playback {

    private val mContext: Context
    private val mWifiLock: WifiManager.WifiLock
    private var mPlayOnFocusGain: Boolean = false
    var mCallback: Playback.Callback? = null
    private val mAudioNoisyReceiverRegistered: Boolean = false
    override var currentMediaId: String? = null

    private var mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
    private val mAudioManager: AudioManager
    private var mExoPlayer: SimpleExoPlayer? = null
    private val mEventListener = ExoPlayerEventListener()

    // Whether to return STATE_NONE or STATE_STOPPED when mExoPlayer is null;
    private var mExoPlayerNullIsStopped = false

    private val mAudioNoisyIntentFilter = IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)


    init {
        val applicationContext = context.applicationContext
        this.mContext = applicationContext

        this.mAudioManager = applicationContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        // Create the Wifi lock (this does not acquire the lock, this just creates it)
        this.mWifiLock = (applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager)
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "uAmp_lock")
    }

    override fun start() {
        // Nothing to do
    }

    override fun stop(notifyListeners: Boolean) {
        giveUpAudioFocus()
        releaseResources(true)
    }

    override // Nothing to do (mExoPlayer holds its own state).
    var state: Int
        get() {
            if (mExoPlayer == null) {
                return if (mExoPlayerNullIsStopped)
                    PlaybackStateCompat.STATE_STOPPED
                else
                    PlaybackStateCompat.STATE_NONE
            }
            when (mExoPlayer!!.playbackState) {
                ExoPlayer.STATE_IDLE -> return PlaybackStateCompat.STATE_PAUSED
                ExoPlayer.STATE_BUFFERING -> return PlaybackStateCompat.STATE_BUFFERING
                ExoPlayer.STATE_READY -> return if (mExoPlayer!!.playWhenReady)
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
        get() = mPlayOnFocusGain || mExoPlayer != null && mExoPlayer!!.playWhenReady

    override val currentStreamPosition: Long
        get() = if (mExoPlayer != null) mExoPlayer!!.currentPosition else 0

    override fun updateLastKnownStreamPosition() {
        // Nothing to do. Position maintained by ExoPlayer.
    }

    override fun play(item: QueueItem?) {
        mPlayOnFocusGain = true
        tryToGetAudioFocus()
        //        String mediaId = item.getDescription().getMediaId();
        //        boolean mediaHasChanged = !TextUtils.equals(mediaId, mCurrentMediaId);
        //        if (mediaHasChanged) {
        //            mCurrentMediaId = mediaId;
        //        }

        //        if (mediaHasChanged || mExoPlayer == null) {
        //            releaseResources(false); // release everything except the player
        //            MediaMetadataCompat track =
        //                    mMusicProvider.getMusic(
        //                            MediaIDHelper.extractMusicIDFromMediaID(
        //                                    item.getDescription().getMediaId()));
        //
        //            String source = track.getString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE);
        //            if (source != null) {
        //                source = source.replaceAll(" ", "%20"); // Escape spaces for URLs
        //            }

        if (mExoPlayer == null) {
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    mContext, DefaultTrackSelector(), DefaultLoadControl())
            mExoPlayer!!.addListener(mEventListener)
        }

        mExoPlayer!!.audioStreamType = AudioManager.STREAM_MUSIC

        // Produces DataSource instances through which media data is loaded.
        val dataSourceFactory = DefaultDataSourceFactory(
                mContext, Util.getUserAgent(mContext, "uamp"), null)
        // Produces Extractor instances for parsing the media data.
        val extractorsFactory = DefaultExtractorsFactory()
        // The MediaSource represents the media to be played.
        val mediaSource = ExtractorMediaSource(
                Uri.parse("http://content.blubrry.com/aa_beyond_belief/Episode_58_The_" + "12_Step_Philosophy_of_Alcoholics_Anonymous.mp3"),
                dataSourceFactory, extractorsFactory, null, null)

        // Prepares media to play (happens on background thread) and triggers
        // {@code onPlayerStateChanged} callback when the stream is ready to play.
        mExoPlayer!!.prepare(mediaSource)

        // If we are streaming from the internet, we want to hold a
        // Wifi lock, which prevents the Wifi radio from going to
        // sleep while the song is playing.
        mWifiLock.acquire()
        //        }

        configurePlayerState()
    }

    override fun pause() {
        // Pause player and cancel the 'foreground service' state.
        if (mExoPlayer != null) {
            mExoPlayer!!.playWhenReady = false
        }
        // While paused, retain the player instance, but give up audio focus.
        releaseResources(false)
    }

    override fun seekTo(position: Long) {
        if (mExoPlayer != null) {
            mExoPlayer!!.seekTo(position)
        }
    }

    override fun setCallback(callback: Playback.Callback?) {
        this.mCallback = callback
    }

    private fun tryToGetAudioFocus() {
        val result = mAudioManager.requestAudioFocus(
                mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN)
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_FOCUSED
        } else {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    private fun giveUpAudioFocus() {
        if (mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener) == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }
    }

    /**
     * Reconfigures the player according to audio focus settings and starts/restarts it. This method
     * starts/restarts the ExoPlayer instance respecting the current audio focus state. So if we
     * have focus, it will play normally; if we don't have focus, it will either leave the player
     * paused or set it to a low volume, depending on what is permitted by the current focus
     * settings.
     */
    private fun configurePlayerState() {
        if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_NO_DUCK) {
            // We don't have audio focus and can't duck, so we have to pause
            pause()
        } else {

            if (mCurrentAudioFocusState == AUDIO_NO_FOCUS_CAN_DUCK) {
                // We're permitted to play, but only if we 'duck', ie: play softly
                mExoPlayer!!.volume = VOLUME_DUCK
            } else {
                mExoPlayer!!.volume = VOLUME_NORMAL
            }

            // If we were playing when we lost focus, we need to resume playing.
            if (mPlayOnFocusGain) {
                mExoPlayer!!.playWhenReady = true
                mPlayOnFocusGain = false
            }
        }
    }

    private val mOnAudioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
        when (focusChange) {
            AudioManager.AUDIOFOCUS_GAIN -> mCurrentAudioFocusState = AUDIO_FOCUSED
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ->
                // Audio focus was lost, but it's possible to duck (i.e.: play quietly)
                mCurrentAudioFocusState = AUDIO_NO_FOCUS_CAN_DUCK
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> {
                // Lost audio focus, but will gain it back (shortly), so note whether
                // playback should resume
                mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
                mPlayOnFocusGain = mExoPlayer != null && mExoPlayer!!.playWhenReady
            }
            AudioManager.AUDIOFOCUS_LOSS ->
                // Lost audio focus, probably "permanently"
                mCurrentAudioFocusState = AUDIO_NO_FOCUS_NO_DUCK
        }

        if (mExoPlayer != null) {
            // Update the player state based on the change
            configurePlayerState()
        }
    }

    /**
     * Releases resources used by the service for playback, which is mostly just the WiFi lock for
     * local playback. If requested, the ExoPlayer instance is also released.

     * @param releasePlayer Indicates whether the player should also be released
     */
    private fun releaseResources(releasePlayer: Boolean) {

        // Stops and releases player (if requested and available).
        if (releasePlayer && mExoPlayer != null) {
            mExoPlayer!!.release()
            mExoPlayer!!.removeListener(mEventListener)
            mExoPlayer = null
            mExoPlayerNullIsStopped = true
            mPlayOnFocusGain = false
        }

        if (mWifiLock.isHeld) {
            mWifiLock.release()
        }
    }

    private inner class ExoPlayerEventListener : ExoPlayer.EventListener {
        override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
            // Nothing to do.
        }

        override fun onTracksChanged(
                trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
            // Nothing to do.
        }

        override fun onLoadingChanged(isLoading: Boolean) {
            // Nothing to do.
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
            // Nothing to do.
        }

    }

    companion object {

        // The volume we set the media player to when we lose audio focus, but are
        // allowed to reduce the volume instead of stopping playback.
        val VOLUME_DUCK = 0.2f
        // The volume we set the media player when we have audio focus.
        val VOLUME_NORMAL = 1.0f

        // we don't have audio focus, and can't duck (play at a low volume)
        private val AUDIO_NO_FOCUS_NO_DUCK = 0
        // we don't have focus, but can duck (play at a low volume)
        private val AUDIO_NO_FOCUS_CAN_DUCK = 1
        // we have full audio focus
        private val AUDIO_FOCUSED = 2
    }
}