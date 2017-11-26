package com.example.android.uamp


import android.content.Context
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.model.MusicProvider
import com.example.android.uamp.utils.QueueHelper
import java.util.*

internal class QueueHelperRealm : QueueHelper {
    private val playingQueue = ArrayList<MediaSessionCompat.QueueItem>()

    override fun getPlayingQueue(mediaId: String, musicProvider: MusicProvider): List<MediaSessionCompat.QueueItem> {
        val metadataCompat = musicProvider.getMusic(mediaId)
        val item = MediaSessionCompat.QueueItem(metadataCompat.description, (playingQueue.size + 1).toLong())
        playingQueue.add(item)
        return Collections.unmodifiableList<MediaSessionCompat.QueueItem>(playingQueue)
    }

    override fun getPlayingQueueFromSearch(query: String, queryParams: Bundle, musicProvider: MusicProvider): List<MediaSessionCompat.QueueItem> {
        throw UnsupportedOperationException()
    }

    override fun getMusicIndexOnQueue(queue: Iterable<MediaSessionCompat.QueueItem>, mediaId: String): Int {
        return 0
    }

    override fun getMusicIndexOnQueue(queue: Iterable<MediaSessionCompat.QueueItem>, queueId: Long): Int {
        return 0
    }

    override fun getRandomQueue(musicProvider: MusicProvider): List<MediaSessionCompat.QueueItem>? {
        return null
    }

    override fun isIndexPlayable(index: Int, queue: List<MediaSessionCompat.QueueItem>): Boolean {
        return (queue?.size > 0) && (queue?.size >= index)
    }

    override fun equals(list1: List<MediaSessionCompat.QueueItem>, list2: List<MediaSessionCompat.QueueItem>): Boolean {
        return false
    }

    override fun isQueueItemPlaying(context: Context, queueItem: MediaSessionCompat.QueueItem): Boolean {
        return false
    }
}
