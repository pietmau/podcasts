package player.model


import android.content.res.Resources
import android.graphics.Bitmap
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat

interface MusicProvider {
    val genres: Iterable<String>?

    val shuffledMusic: Iterable<MediaMetadataCompat>?

    val isInitialized: Boolean

    fun getMusicsByGenre(genre: String): Iterable<MediaMetadataCompat>?

    fun searchMusicBySongTitle(query: String): Iterable<MediaMetadataCompat>?

    fun searchMusicByAlbum(query: String): Iterable<MediaMetadataCompat>?

    fun searchMusicByArtist(query: String): Iterable<MediaMetadataCompat>?

    fun getMusic(musicId: String?): MediaMetadataCompat?

    fun updateMusicArt(musicId: String?, albumArt: Bitmap, icon: Bitmap)

    fun setFavorite(musicId: String?, favorite: Boolean)

    fun isFavorite(musicId: String?): Boolean

    fun retrieveMediaAsync(callback: MusicProviderImpl.Callback)

    fun getChildren(mediaId: String, resources: Resources): List<MediaBrowserCompat.MediaItem>?
}
