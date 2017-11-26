package com.example.android.uamp.model;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;

import java.util.List;

public interface MusicProvider {
    Iterable<String> getGenres();

    Iterable<MediaMetadataCompat> getShuffledMusic();

    Iterable<MediaMetadataCompat> getMusicsByGenre(String genre);

    Iterable<MediaMetadataCompat> searchMusicBySongTitle(String query);

    Iterable<MediaMetadataCompat> searchMusicByAlbum(String query);

    Iterable<MediaMetadataCompat> searchMusicByArtist(String query);

    MediaMetadataCompat getMusic(String musicId);

    void updateMusicArt(String musicId, Bitmap albumArt, Bitmap icon);

    void setFavorite(String musicId, boolean favorite);

    boolean isInitialized();

    boolean isFavorite(String musicId);

    void retrieveMediaAsync(MusicProviderImpl.Callback callback);

    List<MediaBrowserCompat.MediaItem> getChildren(String mediaId, Resources resources);
}
