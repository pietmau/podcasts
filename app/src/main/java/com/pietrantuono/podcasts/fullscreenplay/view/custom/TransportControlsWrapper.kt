package com.pietrantuono.podcasts.fullscreenplay.view.custom

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.player.player.service.CustomMusicService


class TransportControlsWrapper(context: Context) {
    private var mediaBrowser: MediaBrowserCompat? = null
    private var transportControls: MediaControllerCompat.TransportControls? = null
    private var mediaControllerCompat: MediaControllerCompat? = null

    init {
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, CustomMusicService::class.java),
                object : MediaBrowserCompat.ConnectionCallback() {
                    override fun onConnected() {
                        mediaControllerCompat = MediaControllerCompat(context, mediaBrowser?.sessionToken)
                        transportControls = mediaControllerCompat?.transportControls
                    }
                }, null)
    }

    fun onStop() {
        mediaBrowser?.disconnect()
    }

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun playFromMediaId(episode: Episode?) {
        if (episode != null && !episode.link.isNullOrBlank()) {
            transportControls?.playFromMediaId(episode.link, null)
        }
    }
}