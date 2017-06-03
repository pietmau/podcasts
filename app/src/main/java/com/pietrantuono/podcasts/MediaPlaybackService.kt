package com.pietrantuono.podcasts

import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import java.util.*

class MediaPlaybackService : MediaBrowserServiceCompat() {
    private var mMediaSession: MediaSessionCompat? = null
    private var mStateBuilder: PlaybackStateCompat.Builder? = null

    override fun onCreate() {
        super.onCreate()

        // Create a MediaSessionCompat
        mMediaSession = MediaSessionCompat(applicationContext, LOG_TAG)

        // Enable callbacks from MediaButtons and TransportControls
        mMediaSession!!.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        mStateBuilder = PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
        mMediaSession!!.setPlaybackState(mStateBuilder!!.build())

        // MySessionCallback() has methods that handle callbacks from a media controller
        mMediaSession!!.setCallback(MySessionCallback())

        // Set the session's token so that client activities can communicate with it.
        sessionToken = mMediaSession!!.sessionToken
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot(null!!, null)
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

