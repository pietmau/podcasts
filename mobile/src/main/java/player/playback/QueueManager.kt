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

import java.util.ArrayList
import java.util.Arrays
import java.util.Collections

import player.AlbumArtCache
import player.model.MusicProvider
import player.utils.LogHelper
import player.utils.MediaIDHelper
import player.utils.QueueHelper

/**
 * Simple data provider for queues. Keeps track of a current queue and a current index in the
 * queue. Also provides methods to set the current queue based on common queries, relying on a
 * given MusicProvider to provide the actual media metadata.
 */
class QueueManager(private val mMusicProvider: MusicProvider,
                   private val mResources: Resources,
                   private val mListener: MetadataUpdateListener,
                   private val queueHelper: QueueHelper
) {

    // "Now playing" queue:
    private var mPlayingQueue: List<MediaSessionCompat.QueueItem>? = null
    private var mCurrentIndex: Int = 0

    val currentMusic: MediaSessionCompat.QueueItem?
        get() = if (!queueHelper.isIndexPlayable(mCurrentIndex, mPlayingQueue)) {
            null
        } else mPlayingQueue!![mCurrentIndex]

    val currentQueueSize: Int
        get() = if (mPlayingQueue == null) {
            0
        } else mPlayingQueue!!.size

    val playlist: List<MediaBrowserCompat.MediaItem>
        get() = queueHelper.playlist

    init {

        mPlayingQueue = Collections.synchronizedList(ArrayList())
        mCurrentIndex = 0
    }

    fun isSameBrowsingCategory(mediaId: String): Boolean {
        val newBrowseHierarchy = MediaIDHelper.getHierarchy(mediaId)
        val current = currentMusic ?: return false
        val currentBrowseHierarchy = MediaIDHelper.getHierarchy(
                current.description.mediaId!!)

        return Arrays.equals(newBrowseHierarchy, currentBrowseHierarchy)
    }

    private fun setCurrentQueueIndex(index: Int) {
        if (index >= 0 && index < mPlayingQueue!!.size) {
            mCurrentIndex = index
            mListener.onCurrentQueueIndexUpdated(mCurrentIndex)
        }
    }

    fun setCurrentQueueItem(queueId: Long): Boolean {
        // set the current index on queue from the queue Id:
        val index = queueHelper.getMusicIndexOnQueue(mPlayingQueue, queueId)
        setCurrentQueueIndex(index)
        return index >= 0
    }

    fun setCurrentQueueItem(mediaId: String): Boolean {
        // set the current index on queue from the music Id:
        val index = queueHelper.getMusicIndexOnQueue(mPlayingQueue, mediaId)
        setCurrentQueueIndex(index)
        return index >= 0
    }

    fun skipQueuePosition(amount: Int): Boolean {
        var index = mCurrentIndex + amount
        if (index < 0) {
            // skip backwards before the first song will keep you on the first song
            index = 0
        } else {
            // skip forwards when in last song will cycle back to start of the queue
            index %= mPlayingQueue!!.size
        }
        if (!queueHelper.isIndexPlayable(index, mPlayingQueue)) {
            LogHelper.e(TAG, "Cannot increment queue index by ", amount,
                    ". Current=", mCurrentIndex, " queue length=", mPlayingQueue!!.size)
            return false
        }
        mCurrentIndex = index
        return true
    }

    fun setQueueFromSearch(query: String, extras: Bundle): Boolean {
        val queue = queueHelper.getPlayingQueueFromSearch(query, extras, mMusicProvider)
        setCurrentQueue(mResources.getString(R.string.search_queue_title), queue)
        updateMetadata()
        return queue != null && !queue.isEmpty()
    }

    fun setRandomQueue() {
        setCurrentQueue(mResources.getString(R.string.random_queue_title),
                queueHelper.getRandomQueue(mMusicProvider))
        updateMetadata()
    }

    fun setQueueFromMusic(mediaId: String) {
        LogHelper.d(TAG, "setQueueFromMusic", mediaId)
        val queueTitle = ""
        setCurrentQueue(queueTitle,
                queueHelper.getPlayingQueue(mediaId, mMusicProvider), mediaId)
        updateMetadata()
    }


    fun addToQueue(uri: String) {
        val newQueue = queueHelper.getPlayingQueue(uri, mMusicProvider)
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
        val musicId = currentMusic.description.mediaId
        val metadata = mMusicProvider.getMusic(musicId) ?: throw IllegalArgumentException("Invalid musicId " + musicId!!)

        mListener.onMetadataChanged(metadata)

        // Set the proper album artwork on the media session, so it can be shown in the
        // locked screen and in other places.
        if (metadata.description.iconBitmap == null && metadata.description.iconUri != null) {
            val albumUri = metadata.description.iconUri!!.toString()
            AlbumArtCache.getInstance().fetch(albumUri, object : AlbumArtCache.FetchListener() {
                override fun onFetched(artUrl: String, bitmap: Bitmap, icon: Bitmap) {
                    mMusicProvider.updateMusicArt(musicId, bitmap, icon)

                    // If we are still playing the same music, notify the listeners:
                    if (currentMusic == null) {
                        return
                    }
                    val currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(
                            currentMusic.description.mediaId!!)
                    if (musicId == currentPlayingId) {
                        mListener.onMetadataChanged(mMusicProvider.getMusic(currentPlayingId))
                    }
                }
            })
        }
    }

    fun updateProgress(position: Long) {

    }


    interface MetadataUpdateListener {
        fun onMetadataChanged(metadata: MediaMetadataCompat)

        fun onMetadataRetrieveError()

        fun onCurrentQueueIndexUpdated(queueIndex: Int)

        fun onQueueUpdated(title: String, newQueue: List<MediaSessionCompat.QueueItem>?)
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(QueueManager::class.java)
    }
}
