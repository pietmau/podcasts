package com.pietrantuono.podcasts.player.player.service


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.AlbumArtCache
import com.example.android.uamp.playback.QueueManager
import com.example.android.uamp.utils.MediaIDHelper
import com.pietrantuono.podcasts.repository.EpisodesRepository

internal class PodcasteManagerImpl(
        private val episodesRepository: EpisodesRepository,
        private var provider: PodcastProvider,
        private val listener: QueueManager.MetadataUpdateListener)
    : PodcastManager {

    override val currentMusic: MediaSessionCompat.QueueItem?
        get() = queueItem

    private var queueItem: MediaSessionCompat.QueueItem? = null

    override fun skipQueuePosition(position: Int): Boolean {
        return false
    }

    override fun updateMetadata() {
        if (currentMusic == null) {
            listener.onMetadataRetrieveError()
            return
        }
        val musicId = currentMusic?.getDescription()?.getMediaId()
        val metadata = provider.getMusic(musicId) ?: throw IllegalArgumentException("Invalid musicId " + musicId!!)

        listener.onMetadataChanged(metadata)
        if (metadata.getDescription().getIconBitmap() == null && metadata.getDescription().getIconUri() != null) {
            val albumUri = metadata.getDescription().getIconUri()!!.toString()
            AlbumArtCache.getInstance().fetch(albumUri, object : AlbumArtCache.FetchListener() {
                override fun onFetched(artUrl: String, bitmap: Bitmap, icon: Bitmap) {
                    provider.updateMusicArt(musicId, bitmap, icon)

                    // If we are still playing the same music, notify the listeners:
                    val currentMusic = currentMusic ?: return
                    val currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(
                            currentMusic!!.getDescription().getMediaId()!!)
                    if (musicId == currentPlayingId) {
                        listener.onMetadataChanged(provider.getMusic(currentPlayingId))
                    }
                }
            })
        }
    }

    override fun setQueueFromMusic(mediaId: String) {
        queueItem = createMediaSessionCompat(mediaId)
        if (queueItem != null) {
            onQueueUpdated(mediaId)
            updateMetadata()
        }
    }

    private fun onQueueUpdated(mediaId: String) {
        if (queueItem == null) {
            return
        }
        val episode = getEpisodeFromId(mediaId)
        listener.onQueueUpdated(episode?.title, listOf(queueItem))
    }


    override fun setQueueFromSearch(query: String, extras: Bundle): Boolean {
        throw UnsupportedOperationException()
    }

    private fun createMediaSessionCompat(mediaId: String): MediaSessionCompat.QueueItem? {
        return getEpisodeFromId(mediaId)?.let { MediaSessionCompat.QueueItem(createMediaDescriptionCompatFromEpisode(it), DEAFAULT_ID) }
    }

    private fun getEpisodeFromId(mediaId: String) = episodesRepository.getEpisodeByUrl(mediaId)

    companion object {
        private val DEAFAULT_ID = 0L
    }


}
