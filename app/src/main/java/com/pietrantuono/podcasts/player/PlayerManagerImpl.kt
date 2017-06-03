package com.pietrantuono.podcasts.player

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.pietrantuono.podcasts.apis.PodcastFeed


class PlayerManagerImpl(context: Context) : PlayerManager {
    private val mConnectionCallbacks: MediaBrowserCompat.ConnectionCallback
    private var mediaBrowser: MediaBrowserCompat? = null
    private var mediaController: MediaControllerCompat? = null

    init {
        mConnectionCallbacks = object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnectionFailed() {

            }

            override fun onConnectionSuspended() {

            }

            override fun onConnected() {
                val token = mediaBrowser?.getSessionToken()
                mediaController = MediaControllerCompat(context,
                        token)
                // Save the controller
                // MediaControllerCompat.setMediaController(context, mediaController)
            }
        }

        mediaBrowser = MediaBrowserCompat(context,
                ComponentName(context, MediaPlaybackService::class.java),
                mConnectionCallbacks,
                null)
    }

    override fun onStop() {
        // (see "stay in sync with the MediaSession")
//        if (MediaControllerCompat.getMediaController(MediaPlayerActivity.this) != null) {
//            MediaControllerCompat.getMediaController(MediaPlayerActivity.this).unregisterCallback(controllerCallback);
//        }
        mediaBrowser?.disconnect()
    }

    override fun onStart() {
        mediaBrowser?.connect()
    }

    override fun listenToAll(podcastFeed: PodcastFeed?) {
        mediaController?.transportControls?.play()
    }

}
