package com.pietrantuono.podcasts.playlist.presenter

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper
import com.pietrantuono.podcasts.playlist.view.PlayListView


class PlaylistPresenter(
        private var mediaBrowserCompatWrapper: MediaBrowserCompatWrapper)
    : MediaControllerCompat.Callback() {

    private var view: PlayListView? = null

    private val subscriptionCallback = SubscriptionCallback()

    fun bind(view: PlayListView) {
        this.view = view
        mediaBrowserCompatWrapper.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                mediaBrowserCompatWrapper.subscribe(object : SubscriptionCallback() {
                    override fun onChildrenLoaded(parentId: String, playlist: MutableList<MediaBrowserCompat.MediaItem>) {
                        view.onPlaylistRetrieved(playlist)
                    }
                })
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

}