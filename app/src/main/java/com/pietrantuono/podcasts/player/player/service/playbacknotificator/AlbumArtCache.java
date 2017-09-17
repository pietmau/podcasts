package com.pietrantuono.podcasts.player.player.service.playbacknotificator;


import android.graphics.Bitmap;

public class AlbumArtCache {
    public static AlbumArtCache getInstance() {
        throw new UnsupportedOperationException();
    }

    public Bitmap getBigImage(String artUrl)  {
        throw new UnsupportedOperationException();
    }

    public void fetch(String artUrl, FetchListener fetchListener) {

    }

    public interface FetchListener {
        void onFetched(String artUrl, Bitmap bitmap, Bitmap icon);
    }
}
