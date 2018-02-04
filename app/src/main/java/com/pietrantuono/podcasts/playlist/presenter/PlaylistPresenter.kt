package com.pietrantuono.podcasts.playlist.presenter

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.fullscreenplay.customcontrols.MediaBrowserCompatWrapper
import com.pietrantuono.podcasts.playlist.model.PlaylistModel
import com.pietrantuono.podcasts.playlist.view.PlayListView
import com.pietrantuono.podcasts.singlepodcast.presenter.SimpleObserver
import models.pojos.Episode


class PlaylistPresenter(
        private var mediaBrowserCompatWrapper: MediaBrowserCompatWrapper,
        private var playlistModel: PlaylistModel)
    : MediaControllerCompat.Callback() {

    private var view: PlayListView? = null
    private val mediaSubscriptionCallback = SubscriptionCallback()

    fun bind(view: PlayListView) {
        this.view = view
        mediaBrowserCompatWrapper.init(object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                mediaBrowserCompatWrapper.subscribe(object : SubscriptionCallback() {
                    override fun onChildrenLoaded(parentId: String, playlist: MutableList<MediaBrowserCompat.MediaItem>) {
                        onPlaylistRetrieved(playlist)
                    }
                })
            }
        }, this)
        mediaBrowserCompatWrapper.onStart()
    }

    private fun onPlaylistRetrieved(playlist: MutableList<MediaBrowserCompat.MediaItem>) {
        playlistModel.mapItems(playlist, object : SimpleObserver<Episode>() {
            override fun onNext(episode: Episode) {
                view?.onEpisodeRetrieved(episode)
            }
        })
    }

    fun unbind() {
        mediaBrowserCompatWrapper.onStop()
        mediaBrowserCompatWrapper.unsubscribe(mediaSubscriptionCallback)
        mediaBrowserCompatWrapper.unregisterCallback()
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {

    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        view?.currentlyPlayingMediaId = metadata?.description?.mediaId
    }


}