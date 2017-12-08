package com.example.android.uamp.model


import android.content.res.Resources
import android.graphics.Bitmap
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import models.pojos.Episode
import player.model.MusicProvider
import player.model.MusicProviderImpl
import player.model.MusicProviderSource
import player.model.SourceExtractor
import repo.repository.EpisodeCacheImpl
import repo.repository.EpisodesRepository
import repo.repository.EpisodesRepositoryRealm

class MusicProviderRealm(private val extractor: SourceExtractor) : MusicProvider {
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
        val episode = episodesRepository.getEpisodeByUriSync(musicId)
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
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, episode.uri)
        builder.putString(MusicProviderSource.CUSTOM_METADATA_TRACK_SOURCE, getSource(episode))
        builder.putString(MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI, getImageUrl(episode))
        builder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, episode.author)
        episode?.durationInMills?.let { builder.putLong(MediaMetadataCompat.METADATA_KEY_DURATION, it) }
        builder.putString(MediaMetadataCompat.METADATA_KEY_MEDIA_URI, episode.link)
        builder.putString(MediaMetadataCompat.METADATA_KEY_TITLE, episode.title)
        setImageUrl(builder, episode)
        return builder.build()
    }

    private fun setImageUrl(builder: MediaMetadataCompat.Builder, episode: Episode) {
        val imageUrl = getImageUrl(episode)
        builder.putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_ICON_URI, imageUrl)
    }

    private fun getImageUrl(episode: Episode): String? {
        if (!episode.imageUrl.isNullOrEmpty()) {
            return episode.imageUrl
        }
        return episode.imageUrl
    }

    fun getSource(episode: Episode): String? {
        return extractor.getSource(episode)
    }
}
