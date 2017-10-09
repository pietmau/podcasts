package com.pietrantuono.podcasts.player.player.service.provider


import android.graphics.Bitmap
import android.support.v4.media.MediaMetadataCompat

import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository

class PodcastProviderImpl(
        private val repo: EpisodesRepository) : PodcastProvider {

    override fun updateMusicArt(musicId: String?, bitmap: Bitmap?, icon: Bitmap?) {}

    override fun getMusic(mediaId: String?): MediaMetadataCompat? {
        val episode = repo.getEpisodeByUrl(mediaId)
        if (episode == null) {
            return null
        }
        return mediaMetadataCompatFromEpisode(episode)
    }

    private fun mediaMetadataCompatFromEpisode(episode: Episode): MediaMetadataCompat {
        val builder = MediaMetadataCompat.Builder()
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, episode.link)
        val source = getSource(episode)
        if (source != null) {
            builder.putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, source)
            builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, source)
        }
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, episode.author)
        episode?.durationInMills?.let { builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, it) }
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, episode.link)
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, episode.title)
        return builder.build()
    }

    fun getSource(episode: Episode): String? {
        val enclosures = episode.enclosures
        if (enclosures != null && enclosures.size > 0) {
            return enclosures[0].url
        } else {
            return null
        }
    }
}
