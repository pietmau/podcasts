package com.pietrantuono.podcasts.player.player.service.queue


import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.imageloader.SimpleImageLoader
import com.pietrantuono.podcasts.player.player.service.MediaIDHelper
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.repository.EpisodesRepository

/**
 *
 */
internal class QueueManagerImpl(
        private val episodesRepository: EpisodesRepository,
        private var provider: PodcastProvider,
        private var listener: QueueManager.MetadataUpdateListener,
        private val imageLoader: SimpleImageLoader
)
    : QueueManager {

    override val currentMusic: MediaSessionCompat.QueueItem?
        get() = queueItem

    private var queueItem: MediaSessionCompat.QueueItem? = null

    override fun skipQueuePosition(position: Int): Boolean {
        return false
    }

    /** Used by the player service, in its own process */
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
            imageLoader.loadImage(albumUri, object : SimpleImageLoadingListener() {
                override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                    provider.updateMusicArt(musicId, loadedImage, loadedImage)
                    val currentMusic = currentMusic ?: return
                    val currentPlayingId = MediaIDHelper.extractMusicIDFromMediaID(
                            currentMusic.getDescription().getMediaId()!!)
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
