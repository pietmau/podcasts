package com.pietrantuono.podcasts.player.player.service


import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat

interface PodcastManager {

    fun skipQueuePosition(position: Int): Boolean

    fun updateMetadata()

    fun setQueueFromMusic(mediaId: String)

    val currentMusic: MediaSessionCompat.QueueItem?

    fun setQueueFromSearch(query: String, extras: Bundle): Boolean
}
