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

package player.playback

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat

class PlaybackManager(
        private val serviceCallback: PlaybackServiceCallback,
        private val queueManager: QueueManager,
        private val playback: Playback,
        private val playbackStateUpdater: PlaybackStateUpdater,
        private val customActionResolver: CustomActionResolver
) : MediaSessionCompat.Callback(), Playback.Callback {

    val playlist: List<MediaBrowserCompat.MediaItem>
        get() = queueManager.playlist

    init {
        this.playback.setCallback(this)
    }

    fun handlePlayRequest() {
        queueManager.currentMusic?.let { currentMusic ->
            serviceCallback.onPlaybackStart()
            playback.play(currentMusic)
        }
    }

    fun handlePauseRequest() {
        if (playback.isPlaying) {
            playback.pause()
        }
    }

    fun handleStopRequest(withError: String?) {
        playback.stop(true)
        serviceCallback.onPlaybackStop()
        updatePlaybackState(withError)
    }

    fun updatePlaybackState(error: String?) {
        playbackStateUpdater.updatePlaybackState(error, queueManager)
    }

    override fun onCompletion() {
        if (queueManager.skipQueuePosition(1)) {
            handlePlayRequest()
            queueManager.updateMetadata()
        } else {
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
        queueManager.setQueueFromMusic(mediaId)
    }

    fun onNewItemEnqueued(uri: String) {
        if (queueManager.currentQueueSize <= 0) {
            playFromMediaId(uri, null)
            return
        }
        queueManager.addToQueue(uri)
        if (shouldSkipToNext(playback.state)) {
            skipToNext()
            return
        }
    }

    private fun shouldSkipToNext(state: Int): Boolean {
        return (state == PlaybackStateCompat.STATE_STOPPED || state == PlaybackStateCompat.STATE_PAUSED
                || state == PlaybackStateCompat.STATE_NONE || state == PlaybackStateCompat.STATE_ERROR)
    }


    override fun onPlay() {
        if (queueManager.currentMusic == null) {
            queueManager.setRandomQueue()
        }
        handlePlayRequest()
    }

    override fun onSkipToQueueItem(queueId: Long) {
        queueManager.setCurrentQueueItem(queueId)
        queueManager.updateMetadata()
    }

    override fun onSeekTo(position: Long) {
        playback.seekTo(position.toInt().toLong())
    }

    override fun onPlayFromMediaId(mediaId: String, extras: Bundle) {
        playFromMediaId(mediaId, extras)
    }

    override fun onPause() {
        handlePauseRequest()
    }

    override fun onStop() {
        handleStopRequest(null)
    }

    override fun onSkipToNext() {
        this@PlaybackManager.skipToNext()
    }

    override fun onSkipToPrevious() {
        if (queueManager.skipQueuePosition(-1)) {
            handlePlayRequest()
        } else {
            handleStopRequest("Cannot skip")
        }
        queueManager.updateMetadata()
    }

    override fun onCustomAction(action: String, extras: Bundle) {
        customActionResolver.onCustomAction(action, extras, this@PlaybackManager)
    }

    override fun onPlayFromSearch(query: String, extras: Bundle) {
        playback.state = PlaybackStateCompat.STATE_CONNECTING
        val successSearch = queueManager.setQueueFromSearch(query, extras)
        if (successSearch) {
            handlePlayRequest()
            queueManager.updateMetadata()
        } else {
            updatePlaybackState("Could not find music")
        }
    }

    internal fun playFromMediaId(mediaId: String, extras: Bundle?) {
        queueManager.setQueueFromMusic(mediaId)
        handlePlayRequest()
    }

    private fun skipToNext() {
        if (queueManager.skipQueuePosition(1)) {
            handlePlayRequest()
        } else {
            handleStopRequest("Cannot skip")
        }
        queueManager.updateMetadata()
    }

    fun downloadAndAddToQueue(uri: String) {
        serviceCallback.downloadAndAddToQueue(uri)
    }

}
