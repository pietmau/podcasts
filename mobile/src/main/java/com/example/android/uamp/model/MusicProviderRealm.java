package com.example.android.uamp.model;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaMetadataCompat;

import java.util.List;

public class MusicProviderRealm implements MusicProvider {
    @Override
    public Iterable<String> getGenres() {
        return null;
    }

    @Override
    public Iterable<MediaMetadataCompat> getShuffledMusic() {
        return null;
    }

    @Override
    public Iterable<MediaMetadataCompat> getMusicsByGenre(String genre) {
        return null;
    }

    @Override
    public Iterable<MediaMetadataCompat> searchMusicBySongTitle(String query) {
        return null;
    }

    @Override
    public Iterable<MediaMetadataCompat> searchMusicByAlbum(String query) {
        return null;
    }

    @Override
    public Iterable<MediaMetadataCompat> searchMusicByArtist(String query) {
        return null;
    }

    @Override
    public MediaMetadataCompat getMusic(String musicId) {
        return null;
    }

    @Override
    public void updateMusicArt(String musicId, Bitmap albumArt, Bitmap icon) {

    }

    @Override
    public void setFavorite(String musicId, boolean favorite) {

    }

    @Override
    public boolean isInitialized() {
        return false;
    }

    @Override
    public boolean isFavorite(String musicId) {
        return false;
    }

    @Override
    public void retrieveMediaAsync(MusicProviderImpl.Callback callback) {

    }

    @Override
    public List<MediaBrowserCompat.MediaItem> getChildren(String mediaId, Resources resources) {
        return null;
    }
}
