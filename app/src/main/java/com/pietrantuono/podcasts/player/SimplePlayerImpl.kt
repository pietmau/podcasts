package com.pietrantuono.podcasts.player

import android.content.ComponentName
import android.content.Context
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import models.pojos.Episode
import player.MusicService

class SimplePlayerImpl(
        private val context: Context) : SimplePlayer {
    private var episode: Episode? = null
    private var mediaBrowser: MediaBrowserCompat? = null

    override fun playEpisode(episode: Episode?) {
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    connectToSession(mediaBrowser?.sessionToken)
                } catch (e: RemoteException) {
                }
            }
        }, null)
        mediaBrowser?.connect()
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        val transportControls = MediaControllerCompat(context, token)?.transportControls
        transportControls?.stop()
        transportControls?.playFromMediaId(episode?.uri, null)
        onStop()
    }

    override fun onStop() {
        mediaBrowser?.disconnect()
    }
}