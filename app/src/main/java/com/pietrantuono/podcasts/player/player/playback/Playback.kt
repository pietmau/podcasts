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
package com.pietrantuono.podcasts.player.player.playback

import android.support.v4.media.session.MediaSessionCompat.QueueItem
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource

interface Playback {

    var exoPlayer: SimpleExoPlayer?

    /**
     * Start/setup the playback.
     * Resources/listeners would be allocated by implementations.
     */
    fun start()

    /**
     * Stop the playback. All resources can be de-allocated by implementations here.
     * @param notifyListeners if true and a callback has been set by setCallback,
     * *                        callback.onPlaybackStatusChanged will be called after changing
     * *                        the state.
     */
    fun stop(notifyListeners: Boolean)

    /**
     * Get the current [android.media.session.PlaybackState.getState]
     */
    /**
     * Set the latest playback state as determined by the caller.
     */
    var state: Int

    /**
     * @return boolean that indicates that this is ready to be used.
     */
    val isConnected: Boolean

    /**
     * @return boolean indicating whether the simpleExoPlayer is playing or is supposed to be
     * * playing when we gain audio focus.
     */
    val isPlaying: Boolean

    /**
     * @return pos if currently playing an item
     */
    val currentStreamPosition: Long

    /**
     * Queries the underlying stream and update the internal last known stream position.
     */
    fun updateLastKnownStreamPosition()

    fun play(item: QueueItem?)

    fun pause()

    fun seekTo(position: Long)

    var currentMediaId: String?

    interface Callback {
        /**
         * On current music completed.
         */
        fun onCompletion()

        /**
         * on Playback status changed
         * Implementations can use this callback to update
         * playback state on the media sessions.
         */
        fun onPlaybackStatusChanged(state: Int)

        /**
         * @param error to be added to the PlaybackState
         */
        fun onError(error: String?)

        /**
         * @param mediaId being currently played
         */
        fun setCurrentMediaId(mediaId: String?)
    }

    fun setCallback(callback: Callback?)

    fun playAll(mediaSource: MediaSource)
    fun playMediaSource(mediaSource: MediaSource)
    fun setMediaSource(mediaSource: MediaSource)
    fun play()
    fun addListener(listener: ExoPlayer.EventListener)
    fun removeListener(listener: ExoPlayer.EventListener)
}
