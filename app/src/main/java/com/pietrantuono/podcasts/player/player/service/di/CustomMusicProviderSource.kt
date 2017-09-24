package com.pietrantuono.podcasts.player.player.service.di

import android.support.v4.media.MediaMetadataCompat
import com.example.android.uamp.model.MusicProviderSource

class CustomMusicProviderSource: MusicProviderSource {

    override fun iterator(): MutableIterator<MediaMetadataCompat> {
        return mutableListOf<MediaMetadataCompat>().iterator()
    }


}