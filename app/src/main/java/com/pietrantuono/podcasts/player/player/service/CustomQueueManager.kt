package com.pietrantuono.podcasts.player.player.service

import android.content.res.Resources
import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.example.android.uamp.model.MusicProvider
import com.example.android.uamp.playback.QueueManager
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository


class CustomQueueManager(
        private val musicProvider: MusicProvider,
        private val resources: Resources,
        listener: MetadataUpdateListener,
        private val repository: EpisodesRepository
) :
        QueueManager(musicProvider, resources, listener) {

    private val STANDARD_TITLE: String?
        get() = resources.getString(R.string.currently_playing)

    override fun setQueueFromMusic(mediaId: String?) {
        val queue = createQueueFromMediaId(mediaId)
        setCurrentQueue(STANDARD_TITLE, queue, mediaId)
        updateMetadata()
    }

    private fun createQueueFromMediaId(mediaId: String?): List<MediaSessionCompat.QueueItem> {
        val episode = repository.getEpisodeByUrl(mediaId)
        if (episode == null) {
            return emptyList()
        }
        return createQueueItemFromEpisode(episode)
    }
}

fun createQueueItemFromEpisode(episode: Episode): List<MediaSessionCompat.QueueItem> {
    return listOf(createItemFromEpisode(episode))
}

fun createItemFromEpisode(episode: Episode): MediaSessionCompat.QueueItem {
    return MediaSessionCompat.QueueItem(createMediaDescriptionCompatFromEpisode(episode), 0)
}

fun createMediaDescriptionCompatFromEpisode(episode: Episode): MediaDescriptionCompat {
    val builder = MediaDescriptionCompat.Builder()
    builder.setTitle(episode.title).setSubtitle(episode.subtitle).setMediaId(episode.link).setDescription(episode.description)
    episode.imageUrl?.let { builder.setIconUri(Uri.parse(episode.imageUrl)) }
    episode.link?.let { builder.setMediaUri(Uri.parse(episode.link)) }
    return builder.build()
}
