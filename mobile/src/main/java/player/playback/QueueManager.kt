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

import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.R
import player.AlbumArtCache
import player.model.MusicProvider
import player.utils.MediaIDHelper
import player.utils.QueueHelper
import java.util.*

class QueueManager(private val musicProvider: MusicProvider,
                   private val mResources: Resources,
                   private val mListener: MetadataUpdateListener,
                   private val queueHelper: QueueHelper
) {
    private var mPlayingQueue: List<MediaSessionCompat.QueueItem>? = null
    private var mCurrentIndex: Int = 0
    internal val currentMusicMediaId
        get() = currentMusic?.description?.mediaId

    val currentMusic: MediaSessionCompat.QueueItem?
        get() = if (!queueHelper.isIndexPlayable(mCurrentIndex, mPlayingQueue)) {
            null
        } else mPlayingQueue?.get(mCurrentIndex)

    val currentQueueSize: Int
        get() = mPlayingQueue?.size ?: 0

    val playlist: List<MediaBrowserCompat.MediaItem>
        get() = queueHelper.playlist

    init {
        mPlayingQueue = Collections.synchronizedList(ArrayList())
        mCurrentIndex = 0
    }

    private fun setCurrentQueueIndex(index: Int) {
        val size = mPlayingQueue?.size ?: 0
        if (index >= 0 && index < size) {
            mCurrentIndex = index
            mListener.onCurrentQueueIndexUpdated(mCurrentIndex)
        }
    }

    fun setCurrentQueueItem(queueId: Long): Boolean {
        val index = queueHelper.getMusicIndexOnQueue(mPlayingQueue, queueId)
        setCurrentQueueIndex(index)
        return index >= 0
    }

    fun skipQueuePosition(amount: Int): Boolean {
        var index = mCurrentIndex + amount
        if (index < 0) {
            index = 0
        } else {
            index %= mPlayingQueue?.size ?: 0
        }
        if (!queueHelper.isIndexPlayable(index, mPlayingQueue)) {
            return false
        }
        mCurrentIndex = index
        return true
    }

    fun setQueueFromSearch(query: String, extras: Bundle): Boolean {
        val queue = queueHelper.getPlayingQueueFromSearch(query, extras, musicProvider)
        setCurrentQueue(mResources.getString(R.string.search_queue_title), queue)
        updateMetadata()
        return queue != null && !queue.isEmpty()
    }

    fun setRandomQueue() {
        setCurrentQueue(mResources.getString(R.string.random_queue_title),
                queueHelper.getRandomQueue(musicProvider))
        updateMetadata()
    }

    fun setQueueFromMusic(mediaId: String) {
        val queueTitle = ""
        setCurrentQueue(queueTitle, queueHelper.getPlayingQueue(mediaId, musicProvider), mediaId)
        updateMetadata()
    }


    fun addToQueue(uri: String) {
        val newQueue = queueHelper.getPlayingQueue(uri, musicProvider)
        mPlayingQueue = newQueue
        updateMetadata()
    }

    @JvmOverloads protected fun setCurrentQueue(title: String, newQueue: List<MediaSessionCompat.QueueItem>?,
                                                initialMediaId: String? = null) {
        mPlayingQueue = newQueue
        var index = 0
        if (initialMediaId != null) {
            index = queueHelper.getMusicIndexOnQueue(mPlayingQueue, initialMediaId)
        }
        mCurrentIndex = Math.max(index, 0)
        mListener.onQueueUpdated(title, newQueue)
    }

    fun updateMetadata() {
        val currentMusic = currentMusic
        if (currentMusic == null) {
            mListener.onMetadataRetrieveError()
            return
        }
        musicProvider.getMusic(currentMusic.description?.mediaId)?.let {
            updateMedtadataInternal(it, currentMusic)
        }
    }

    private fun updateMedtadataInternal(mediaMetadataCompat: MediaMetadataCompat, currentMusic: MediaSessionCompat.QueueItem) {
        mListener.onMetadataChanged(mediaMetadataCompat)
        if (mediaMetadataCompat.description.iconBitmap == null && mediaMetadataCompat.description?.iconUri != null) {
            updateArt(mediaMetadataCompat, currentMusic)
        }
    }

    private fun updateArt(mediaMetadataCompat: MediaMetadataCompat, currentMusic: MediaSessionCompat.QueueItem) {
        AlbumArtCache.getInstance().fetch(mediaMetadataCompat.description?.iconUri?.toString(), object : AlbumArtCache.FetchListener() {
            override fun onFetched(artUrl: String, bitmap: Bitmap, icon: Bitmap) {
                musicProvider.updateMusicArt(mediaMetadataCompat.description?.mediaId, bitmap, icon)
                val mediaId = currentMusic?.description?.mediaId ?: return
                val currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(mediaId)
                if (mediaId == currentPlayingId) {
                    musicProvider.getMusic(currentPlayingId)?.let { mListener.onMetadataChanged(it) }
                }
            }
        })
    }

    interface MetadataUpdateListener {
        fun onMetadataChanged(metadata: MediaMetadataCompat)

        fun onMetadataRetrieveError()

        fun onCurrentQueueIndexUpdated(queueIndex: Int)

        fun onQueueUpdated(title: String, newQueue: List<MediaSessionCompat.QueueItem>?)
    }
}
