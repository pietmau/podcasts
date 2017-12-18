package com.pietrantuono.podcasts.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import com.tonyodev.fetch.request.RequestInfo
import player.MusicService
import player.playback.PlaybackManager.CUSTOM_ACTION_ADD_TO_QUEUE
import player.playback.PlaybackManager.EXTRA_EPISODE_URI
import repo.repository.EpisodesRepository
import java.util.concurrent.CopyOnWriteArraySet

class EnqueuerImpl(
        private val context: Context,
        private val repo: EpisodesRepository
) : Enqueuer {

    private val requestsToBeEnqueued: MutableSet<Long> = CopyOnWriteArraySet<Long>()

    fun enqueueEpisode(uri: String) {
        var mediaBrowser: MediaBrowserCompat? = null
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    connectToSession(mediaBrowser!!, uri)
                } catch (e: RemoteException) {
                }
            }
        }, null)
        mediaBrowser?.connect()
    }

    @Throws(RemoteException::class)
    private fun connectToSession(mediaBrowser: MediaBrowserCompat, uri: String) {
        val mediaControllerCompat = MediaControllerCompat(context, mediaBrowser.sessionToken)
        val transportControls = mediaControllerCompat?.transportControls
        val bundle = Bundle()
        bundle.putString(EXTRA_EPISODE_URI, uri)
        transportControls?.sendCustomAction(CUSTOM_ACTION_ADD_TO_QUEUE, bundle)
    }

    override fun addToQueueIfAppropriate(requestInfo: RequestInfo) {
        if (requestsToBeEnqueued.contains(requestInfo.id)) {
            repo.getEpisodeByDownloadIdSync(requestInfo.id)?.uri?.let {
                requestsToBeEnqueued.remove(requestInfo.id)
                enqueueEpisode(it)
            }
        }
    }

    override fun storeRequestIfAppropriate(requestInfo: RequestInfo?, playWhenReady: Boolean) {
        if (playWhenReady && requestInfo?.id != null) {
            requestsToBeEnqueued.add(requestInfo.id)
        }
    }
}