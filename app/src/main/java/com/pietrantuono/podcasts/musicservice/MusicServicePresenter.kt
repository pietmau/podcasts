package com.pietrantuono.podcasts.musicservice


import android.app.Service.START_STICKY
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import player.Constants
import player.CustomMediaService
import player.MediaNotificationManager
import player.OtherActions
import player.playback.PlaybackManager

class MusicServicePresenter(
        private val session: MediaSessionCompat,
        private val playbackManager: PlaybackManager,
        private val delayedStopHandler: DelayedStopHandler,
        private val otherActions: OtherActions
) {
    internal var service: CustomMediaService? = null
    private var mMediaNotificationManager: MediaNotificationManager? = null

    val playlist: List<MediaBrowserCompat.MediaItem>
        get() = playbackManager.playlist

    init {
        this.delayedStopHandler.setPresenter(this)
    }

    fun onCreate() {
        service?.setSessionToken(session.sessionToken)
        session.setCallback(playbackManager)
        session.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        session.setExtras(Bundle())
        playbackManager.updatePlaybackState(null)
        try {
            mMediaNotificationManager = MediaNotificationManager(service as MusicService?)
        } catch (e: RemoteException) {
            throw IllegalStateException("Could not create a MediaNotificationManager", e)
        }

    }

    fun onStartCommand(startIntent: Intent?, startId: Int): Int {
        if (startIntent != null) {
            if (Constants.ACTION_CMD == startIntent.action) {
                if (Constants.CMD_PAUSE == startIntent.getStringExtra(Constants.CMD_NAME)) {
                    playbackManager.handlePauseRequest()
                }
            }
        }
        delayedStopHandler.removeCallbacksAndMessagesAndSendEmptyMessage()
        return START_STICKY
    }

    fun onDestroy() {
        playbackManager.handleStopRequest(null)
        mMediaNotificationManager?.stopNotification()
        delayedStopHandler.removeCallbacksAndMessages(null)
        session.release()
    }

    fun onPlayBackStart() {
        session.isActive = true
        delayedStopHandler.removeCallbacksAndMessages(null)
        service?.startService(Intent(service?.applicationContext, com.pietrantuono.podcasts.musicservice.MusicService::class.java))
    }

    fun onPlaybackStop() {
        session.isActive = false
        delayedStopHandler.removeCallbacksAndMessagesAndSendEmptyMessage()
        service?.stopForeground(true)
    }

    fun onNotificationRequired() {
        mMediaNotificationManager?.startNotification()
    }

    fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        session.setPlaybackState(newState)
    }

    fun stopSelf() {
        service?.stopSelf()
    }

    fun downloadAndAddToQueue(uri: String) {
        otherActions.downloadAndAddToQueue(uri, service)
    }

}
