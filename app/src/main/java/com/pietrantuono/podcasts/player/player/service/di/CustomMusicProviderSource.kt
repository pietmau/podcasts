package com.pietrantuono.podcasts.player.player.service.di

import android.support.v4.media.MediaMetadataCompat

class CustomMusicProviderSource {
    companion object {
        val CUSTOM_METADATA_TRACK_SOURCE = "__SOURCE__"
    }

    fun iterator(): MutableIterator<MediaMetadataCompat> {
        return mutableListOf<MediaMetadataCompat>().iterator()
    }


}