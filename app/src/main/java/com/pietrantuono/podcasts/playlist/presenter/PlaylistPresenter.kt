package com.pietrantuono.podcasts.playlist.presenter

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper


class PlaylistPresenter(
        private var mediaBrowserCompatWrapper: MediaBrowserCompatWrapper)
    : MediaControllerCompat.Callback() {

    private val subscriptionCallback = SubscriptionCallback()

    fun bind() {
        mediaBrowserCompatWrapper.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                mediaBrowserCompatWrapper.subscribe(subscriptionCallback)
            }
        }, this)
        mediaBrowserCompatWrapper.onStart()
    }

    fun unbind() {
        mediaBrowserCompatWrapper.onStop()
        mediaBrowserCompatWrapper?.unsubscribe(subscriptionCallback)
        mediaBrowserCompatWrapper?.unregisterCallback()
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {

    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {

    }

    class SubscriptionCallback : MediaBrowserCompat.SubscriptionCallback() {

        override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {

        }
    }
}