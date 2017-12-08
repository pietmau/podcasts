package com.pietrantuono.podcasts.playlist.view

import android.content.ComponentName
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.playlist.di.PlaylistModule
import com.pietrantuono.podcasts.playlist.presenter.PlaylistPresenter
import player.MusicService
import javax.inject.Inject


class PlaylistFragment : Fragment() {
    @Inject lateinit var presenter: PlaylistPresenter
    private var mMediaBrowser: MediaBrowserCompat? = null

    companion object {
        const val TAG = "PlaylistFragment"

        fun navigateToPlaylist(fragmentManager: FragmentManager) {
            var frag = fragmentManager.findFragmentByTag(TAG) ?: PlaylistFragment()
            fragmentManager.beginTransaction()?.replace(R.id.fragmentContainer, frag, TAG)?.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity.applicationContext as? App)?.applicationComponent?.with(PlaylistModule())?.inject(this)
    }


    override fun onStart() {
        super.onStart()
        val componentName = ComponentName(activity.applicationContext, MusicService::class.java)
        mMediaBrowser = MediaBrowserCompat(activity.applicationContext, componentName, object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                this@PlaylistFragment.onConnected()
            }
        }, null)
        mMediaBrowser?.connect()
    }

    private fun onConnected() {
        mMediaBrowser?.subscribe("fff", object : MediaBrowserCompat.SubscriptionCallback() {
            override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>) {

            }

            override fun onChildrenLoaded(parentId: String, children: MutableList<MediaBrowserCompat.MediaItem>, options: Bundle) {

            }

            override fun onError(parentId: String) {

            }

            override fun onError(parentId: String, options: Bundle) {

            }
        })

        val mediaController = MediaControllerCompat(activity.applicationContext, mMediaBrowser?.sessionToken)

        val metadata = mediaController.metadata
        val pbState = mediaController.playbackState

        mediaController.registerCallback(object :MediaControllerCompat.Callback(){
            override fun onCaptioningEnabledChanged(enabled: Boolean) {
                super.onCaptioningEnabledChanged(enabled)
            }

            override fun binderDied() {
                super.binderDied()
            }

            override fun onQueueChanged(queue: MutableList<MediaSessionCompat.QueueItem>?) {
                super.onQueueChanged(queue)
            }

            override fun onQueueTitleChanged(title: CharSequence?) {
                super.onQueueTitleChanged(title)
            }

            override fun onRepeatModeChanged(repeatMode: Int) {
                super.onRepeatModeChanged(repeatMode)
            }

            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                super.onPlaybackStateChanged(state)
            }

            override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                super.onMetadataChanged(metadata)
            }

            override fun onExtrasChanged(extras: Bundle?) {
                super.onExtrasChanged(extras)
            }

            override fun onSessionEvent(event: String?, extras: Bundle?) {
                super.onSessionEvent(event, extras)
            }

            override fun onShuffleModeChanged(enabled: Boolean) {
                super.onShuffleModeChanged(enabled)
            }

            override fun onSessionDestroyed() {
                super.onSessionDestroyed()
            }

            override fun onAudioInfoChanged(info: MediaControllerCompat.PlaybackInfo?) {
                super.onAudioInfoChanged(info)
            }
        })
    }

    private fun ff() {

    }
}