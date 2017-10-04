package com.pietrantuono.podcasts.player.player.service.queue


import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat

interface QueueManager {
    fun skipQueuePosition(position: Int): Boolean

    fun updateMetadata()

    fun setQueueFromMusic(mediaId: String)

    val currentMusic: MediaSessionCompat.QueueItem?

    fun setQueueFromSearch(query: String, extras: Bundle): Boolean


    interface MetadataUpdateListener {

        fun onMetadataChanged(metadata: MediaMetadataCompat?)
        fun onMetadataRetrieveError()
        fun onCurrentQueueIndexUpdated(queueIndex: Int)
        fun onQueueUpdated(title: String?, newQueue: List<MediaSessionCompat.QueueItem?>?)
    }

}
