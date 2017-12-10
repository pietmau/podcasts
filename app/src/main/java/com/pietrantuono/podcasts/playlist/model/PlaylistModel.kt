package com.pietrantuono.podcasts.playlist.model

import android.support.v4.media.MediaBrowserCompat
import models.pojos.Episode
import rx.Observer

interface PlaylistModel {
    fun mapItems(playlist: MutableList<MediaBrowserCompat.MediaItem>, observer: Observer<Episode>)

    fun unsubscribe()
}