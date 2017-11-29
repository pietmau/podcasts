package com.example.android.uamp.model


import android.content.res.Resources
import android.graphics.Bitmap
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import models.pojos.Episode
import player.model.MusicProvider
import player.model.MusicProviderImpl
import player.model.MusicProviderSource
import repo.repository.EpisodeCacheImpl
import repo.repository.EpisodesRepository
import repo.repository.EpisodesRepositoryRealm

class MusicProviderRealm : MusicProvider {
    private var episodesRepository: EpisodesRepository

    init {
        episodesRepository = EpisodesRepositoryRealm(EpisodeCacheImpl())
    }

    override fun getGenres(): Iterable<String>? {
        return null
    }

    override fun getShuffledMusic(): Iterable<MediaMetadataCompat>? {
        return null
    }

    override fun getMusicsByGenre(genre: String): Iterable<MediaMetadataCompat>? {
        return null
    }

    override fun searchMusicBySongTitle(query: String): Iterable<MediaMetadataCompat>? {
        return null
    }

    override fun searchMusicByAlbum(query: String): Iterable<MediaMetadataCompat>? {
        return null
    }

    override fun searchMusicByArtist(query: String): Iterable<MediaMetadataCompat>? {
        return null
    }

    override fun getMusic(musicId: String?): MediaMetadataCompat? {
        val episode = episodesRepository.getEpisodeByTitleSync(musicId)
        if (episode == null) {
            return null
        }
        return mediaMetadataCompatFromEpisode(episode)
    }

    override fun updateMusicArt(musicId: String, albumArt: Bitmap, icon: Bitmap) {

    }

    override fun setFavorite(musicId: String?, favorite: Boolean) {
        throw UnsupportedOperationException()
    }

    override fun isInitialized(): Boolean {
        return false
    }

    override fun isFavorite(musicId: String?): Boolean {
        return false
    }

    override fun retrieveMediaAsync(callback: MusicProviderImpl.Callback) {
        throw UnsupportedOperationException()
    }

    override fun getChildren(mediaId: String, resources: Resources): List<MediaBrowserCompat.MediaItem>? {
        return null
    }

    private fun mediaMetadataCompatFromEpisode(episode: Episode): MediaMetadataCompat {
        val builder = MediaMetadataCompat.Builder()
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, episode.title)
        builder.putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, getSource(episode))
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, episode.imageUrl)
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
