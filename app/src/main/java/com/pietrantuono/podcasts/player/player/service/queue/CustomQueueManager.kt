package com.pietrantuono.podcasts.player.player.service.queue

import android.net.Uri
import android.support.v4.media.MediaDescriptionCompat
import pojos.Episode

fun createMediaDescriptionCompatFromEpisode(episode: Episode): MediaDescriptionCompat {
    val builder = MediaDescriptionCompat.Builder()
    builder.setTitle(episode.title).setSubtitle(episode.subtitle).setMediaId(episode.link).setDescription(episode.description)
    episode.imageUrl?.let { builder.setIconUri(Uri.parse(episode.imageUrl)) }
    episode.link?.let { builder.setMediaUri(Uri.parse(episode.link)) }
    return builder.build()
}
