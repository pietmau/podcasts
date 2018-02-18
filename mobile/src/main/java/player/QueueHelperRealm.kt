package player


import android.content.Context
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import player.model.MusicProvider
import player.utils.QueueHelper
import java.util.*

class QueueHelperRealm : QueueHelper {
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
        var result = 0
        for ((index, item) in queue.withIndex()) {
            if (item.description?.mediaId?.equals(mediaId, true) == true) {
                result = index
                break
            }
        }
        return result
    }

    override fun getMusicIndexOnQueue(queue: Iterable<MediaSessionCompat.QueueItem>, queueId: Long): Int {
        var result = 0
        for ((index, item) in queue.withIndex()) {
            if (item.queueId == queueId) {
                result = index
                break
            }
        }
        return result
    }

    override fun getRandomQueue(musicProvider: MusicProvider): List<MediaSessionCompat.QueueItem>? {
        throw UnsupportedOperationException()
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

    override fun getPlaylist(): MutableList<out MediaBrowserCompat.MediaItem> {
        val result = mutableListOf<MediaBrowserCompat.MediaItem>()
        for (queueItem in playingQueue) {
            result.add(createItem(queueItem))
        }
        return result
    }

    private fun createItem(queueItem: MediaSessionCompat.QueueItem): MediaBrowserCompat.MediaItem {
        return MediaBrowserCompat.MediaItem(queueItem.description, MediaBrowserCompat.MediaItem.FLAG_BROWSABLE)
    }
}
