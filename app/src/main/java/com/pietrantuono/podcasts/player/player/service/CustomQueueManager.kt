package com.pietrantuono.podcasts.player.player.service

import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import com.pietrantuono.podcasts.apis.Episode


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
