package com.pietrantuono.podcasts.player.player.service.provider

import android.support.v4.media.MediaMetadataCompat

class MusicProviderSource {
    companion object {
        val CUSTOM_METADATA_TRACK_SOURCE = "__SOURCE__"
    }

    fun iterator(): MutableIterator<MediaMetadataCompat> {
        return mutableListOf<MediaMetadataCompat>().iterator()
    }


}