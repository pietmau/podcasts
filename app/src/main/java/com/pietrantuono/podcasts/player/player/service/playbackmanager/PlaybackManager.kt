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

package com.pietrantuono.podcasts.player.player.service.playbackmanager

import android.os.Bundle
import android.os.SystemClock
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

import com.pietrantuono.podcasts.player.player.LogHelper
import com.pietrantuono.podcasts.player.player.service.playback.Playback
import com.pietrantuono.podcasts.player.player.service.queue.QueueManager

/**
 * Manage the interactions among the container service, the queue manager and the actual playback.
 */
class PlaybackManager(private val mServiceCallback: PlaybackServiceCallback, private val mQueueManager: QueueManager,
                      val playback: Playback?) : Playback.Callback {
    private val mMediaSessionCallback: MediaSessionCallback

    init {
        mMediaSessionCallback = MediaSessionCallback()
        this.playback!!.setCallback(this)
    }

    val mediaSessionCallback: MediaSessionCompat.Callback
        get() = mMediaSessionCallback

    /**
     * Handle a request to play music
     */
    fun handlePlayRequest() {
        LogHelper.d(TAG, "handlePlayRequest: mState=" + playback!!.state)
        val currentMusic = mQueueManager.currentMusic
        if (currentMusic != null) {
            mServiceCallback.onPlaybackStart()
            playback.play(currentMusic)
        }
    }

    fun handlePauseRequest() {
        LogHelper.d(TAG, "handlePauseRequest: mState=" + playback!!.state)
        if (playback.isPlaying) {
            playback.pause()
            mServiceCallback.onPlaybackStop()
        }
    }

    fun handleStopRequest(withError: String?) {
        LogHelper.d(TAG, "handleStopRequest: mState=" + playback!!.state + " error=", withError)
        playback.stop(true)
        mServiceCallback.onPlaybackStop()
        updatePlaybackState(withError)
    }

    fun updatePlaybackState(error: String?) {
        LogHelper.d(TAG, "updatePlaybackState, playback state=" + playback!!.state)
        var position = PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN
        if (playback != null && playback.isConnected) {
            position = playback.currentStreamPosition
        }
        val stateBuilder = PlaybackStateCompat.Builder()
                .setActions(availableActions)
        setCustomAction(stateBuilder)
        var state = playback.state
        if (error != null) {
            stateBuilder.setErrorMessage(error)
            state = PlaybackStateCompat.STATE_ERROR
        }
        stateBuilder.setState(state, position, 1.0f, SystemClock.elapsedRealtime())
        val currentMusic = mQueueManager.currentMusic
        if (currentMusic != null) {
            stateBuilder.setActiveQueueItemId(currentMusic.queueId)
        }
        mServiceCallback.onPlaybackStateUpdated(stateBuilder.build())
        if (state == PlaybackStateCompat.STATE_PLAYING || state == PlaybackStateCompat.STATE_PAUSED) {
            mServiceCallback.onNotificationRequired()
        }
    }

    private fun setCustomAction(stateBuilder: PlaybackStateCompat.Builder) {

    }

    private val availableActions: Long
        get() {
            var actions = PlaybackStateCompat.ACTION_PLAY_PAUSE or
                    PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                    PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
                    PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                    PlaybackStateCompat.ACTION_SKIP_TO_NEXT
            if (playback!!.isPlaying) {
                actions = actions or PlaybackStateCompat.ACTION_PAUSE
            } else {
                actions = actions or PlaybackStateCompat.ACTION_PLAY
            }
            return actions
        }

    /**
     * Implementation of the Playback.Callback interface
     */
    override fun onCompletion() {
        // The media player finished playing the current song, so we go ahead
        // and start the next.
        if (mQueueManager.skipQueuePosition(1)) {
            handlePlayRequest()
            mQueueManager.updateMetadata()
        } else {
            // If skipping was not possible, we stop and release the resources:
            handleStopRequest(null)
        }
    }

    override fun onPlaybackStatusChanged(state: Int) {
        updatePlaybackState(null)
    }

    override fun onError(error: String) {
        updatePlaybackState(error)
    }

    override fun setCurrentMediaId(mediaId: String) {
        throw UnsupportedOperationException("Chromecast unsupported")
    }


    private inner class MediaSessionCallback : MediaSessionCompat.Callback() {
        override fun onPlay() {
            if (mQueueManager.currentMusic == null) {
                return
            }
            handlePlayRequest()
        }

        override fun onSkipToQueueItem(queueId: Long) {
            throw UnsupportedOperationException()
            //mQueueManager.setCurrentQueueItem(queueId);
            //mQueueManager.updateMetadata();
        }

        override fun onSeekTo(position: Long) {
            LogHelper.d(TAG, "onSeekTo:", position)
            playback!!.seekTo(position.toInt().toLong())
        }

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            LogHelper.d(TAG, "playFromMediaId mediaId:", mediaId, "  extras=", extras)
            mQueueManager.setQueueFromMusic(mediaId!!)
            handlePlayRequest()
        }

        override fun onPause() {
            LogHelper.d(TAG, "pause. current state=" + playback!!.state)
            handlePauseRequest()
        }

        override fun onStop() {
            LogHelper.d(TAG, "stop. current state=" + playback!!.state)
            handleStopRequest(null)
        }

        override fun onSkipToNext() {
            LogHelper.d(TAG, "skipToNext")
            if (mQueueManager.skipQueuePosition(1)) {
                handlePlayRequest()
            } else {
                handleStopRequest("Cannot skip")
            }
            mQueueManager.updateMetadata()
        }

        override fun onSkipToPrevious() {
            if (mQueueManager.skipQueuePosition(-1)) {
                handlePlayRequest()
            } else {
                handleStopRequest("Cannot skip")
            }
            mQueueManager.updateMetadata()
        }

        override fun onCustomAction(action: String, extras: Bundle?) {
            throw UnsupportedOperationException()
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            LogHelper.d(TAG, "playFromSearch  query=", query, " extras=", extras)
            playback!!.state = PlaybackStateCompat.STATE_CONNECTING
            val successSearch = mQueueManager.setQueueFromSearch(query!!, extras!!)
            if (successSearch) {
                handlePlayRequest()
                mQueueManager.updateMetadata()
            } else {
                updatePlaybackState("Could not find music")
            }
        }
    }

    interface PlaybackServiceCallback {
        fun onPlaybackStart()

        fun onNotificationRequired()

        fun onPlaybackStop()

        fun onPlaybackStateUpdated(newState: PlaybackStateCompat)
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(PlaybackManager::class.java)
    }
}
