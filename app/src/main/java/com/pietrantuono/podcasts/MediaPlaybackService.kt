package com.pietrantuono.podcasts

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import java.util.*

class MediaPlaybackService : MediaBrowserServiceCompat() {
    private var mediaSession: MediaSessionCompat? = null
    private var stateBuilder: PlaybackStateCompat.Builder? = null

    override fun onCreate() {
        super.onCreate()
        mediaSession = MediaSessionCompat(applicationContext, LOG_TAG)
        mediaSession!!.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        stateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
        mediaSession!!.setPlaybackState(stateBuilder!!.build())
        mediaSession!!.setCallback(MySessionCallback(this))
        sessionToken = mediaSession!!.sessionToken
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot("", null)
    }

    override fun onLoadChildren(parentMediaId: String,
                                result: MediaBrowserServiceCompat.Result<List<MediaBrowserCompat.MediaItem>>) {
        val mediaItems = ArrayList<MediaBrowserCompat.MediaItem>()
        result.sendResult(mediaItems)
    }

    companion object {
        private val LOG_TAG = MediaPlaybackService::class.java.simpleName
    }
}

