package com.pietrantuono.podcasts.player

import android.content.ComponentName
import android.content.Context
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import com.tonyodev.fetch.request.RequestInfo
import models.pojos.Episode
import player.MusicService
import player.playback.PlaybackManager.CUSTOM_ACTION_ADD_TO_QUEUE
import player.playback.PlaybackManager.EXTRA_EPISODE_URI
import repo.repository.EpisodesRepository

class EnqueuerImpl(
        private val context: Context,
        private val repo: EpisodesRepository
) : Enqueuer {
    private var mediaBrowser: MediaBrowserCompat? = null
    private val uris: Set<Episode> = mutableSetOf()

    fun enqueueEpisode(uri: String) {
        mediaBrowser = MediaBrowserCompat(context, ComponentName(context, MusicService::class.java), object : MediaBrowserCompat.ConnectionCallback() {
            override fun onConnected() {
                try {
                    connectToSession(mediaBrowser?.sessionToken, uri)
                } catch (e: RemoteException) {
                }
            }
        }, null)
        mediaBrowser?.connect()
    }

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?, uri: String) {
        val transportControls = MediaControllerCompat(context, token)?.transportControls
        transportControls?.stop()
        val bundle = Bundle()
        bundle.putString(EXTRA_EPISODE_URI, uri)
        transportControls?.sendCustomAction(CUSTOM_ACTION_ADD_TO_QUEUE, bundle)
        mediaBrowser?.disconnect()
    }

    override fun addToQueueIfAppropriate(intent: RequestInfo) {
        repo.getEpisodeByDownloadIdSync(intent.id)?.uri?.let {
            enqueueEpisode(it)
        }
    }
}