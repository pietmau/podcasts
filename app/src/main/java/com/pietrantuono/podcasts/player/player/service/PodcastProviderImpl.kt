package com.pietrantuono.podcasts.player.player.service


import android.graphics.Bitmap
import android.support.v4.media.MediaMetadataCompat
import com.example.android.uamp.model.MusicProviderSource
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.repository.EpisodesRepository

class PodcastProviderImpl(
        private val repo: EpisodesRepository) : PodcastProvider {
    override fun updateMusicArt(musicId: String?, bitmap: Bitmap, icon: Bitmap) {
        TODO("not implemented")
    }

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
        if (getSource(episode) != null) {
            builder.putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, getSource(episode))
        }
//                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, album)
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, episode.author)
        getDuration(episode)?.let { builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, it) }
//                .putString(MediaMetadataCompat.METADATA_KEY_GENRE, genre)
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, episode.imageUrl)
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, episode.title)
//                .putLong(MediaMetadataCompat.METADATA_KEY_TRACK_NUMBER, trackNumber.toLong())
//                .putLong(MediaMetadataCompat.METADATA_KEY_NUM_TRACKS, totalTrackCount.toLong())
//                .build()

        return builder.build()
    }

    private fun getDuration(episode: Episode): Long? {
        if (episode.duration == null) {
            return null;
        }
        return null
    }

    fun getSource(episode: Episode): String? {
        val enclosures = episode.enclosures
        if (enclosures != null && enclosures.size > 0) {
            return enclosures[0].url
        }
        return null
    }
}
