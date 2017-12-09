package com.pietrantuono.podcasts.playlist.view

import android.support.v4.media.MediaBrowserCompat

interface PlayListView {
    fun onPlaylistRetrieved(playlist: MutableList<MediaBrowserCompat.MediaItem>)

}