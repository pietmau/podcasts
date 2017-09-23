package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.content.res.Resources
import com.example.android.uamp.model.MusicProvider
import com.example.android.uamp.playback.QueueManager


class CustomQueueManager(
        musicProvider: MusicProvider,
        resources: Resources,
        listener: MetadataUpdateListener) :
        QueueManager(musicProvider, resources, listener) {

    override fun setQueueFromMusic(mediaId: String?) {

        updateMetadata()
    }
}