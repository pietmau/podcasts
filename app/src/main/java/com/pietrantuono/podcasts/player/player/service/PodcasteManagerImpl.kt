package com.pietrantuono.podcasts.player.player.service


import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.playback.QueueManager
import com.pietrantuono.podcasts.repository.EpisodesRepository

internal class PodcasteManagerImpl(
        private val episodesRepository: EpisodesRepository,
        private val listener: QueueManager.MetadataUpdateListener)
    : PodcastManager {
    override val currentMusic: MediaSessionCompat.QueueItem?
        get() = queueItem

    private var queueItem: MediaSessionCompat.QueueItem? = null

    override fun skipQueuePosition(position: Int): Boolean {
        return false
    }

    override fun updateMetadata() {
        //throw UnsupportedOperationException() TODO
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
