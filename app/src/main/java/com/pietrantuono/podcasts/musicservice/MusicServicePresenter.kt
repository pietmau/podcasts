package com.pietrantuono.podcasts.musicservice


import android.app.Service.START_STICKY
import android.content.Intent
import android.os.Bundle
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import player.*
import player.playback.PlaybackManager

class MusicServicePresenter(
        private val mSession: MediaSessionCompat,
        private val mPlaybackManager: PlaybackManager,
        private val mDelayedStopHandler: DelayedStopHandler,
        private val otherActions: OtherActions
) {
    internal var service: CustomMediaService? = null
    private var mMediaNotificationManager: MediaNotificationManager? = null

    val playlist: List<MediaBrowserCompat.MediaItem>
        get() = mPlaybackManager.playlist

    init {
        this.mDelayedStopHandler.setPresenter(this)
    }

    fun onCreate() {
        service?.setSessionToken(mSession.sessionToken)
        mSession.setCallback(mPlaybackManager)
        mSession.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        //TODO mSession.setSessionActivity(pi);
        mSession.setExtras(Bundle())
        mPlaybackManager.updatePlaybackState(null)
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
                    mPlaybackManager.handlePauseRequest()
                }
            }
        }
        mDelayedStopHandler.removeCallbacksAndMessagesAndSendEmptyMessage()
        return START_STICKY
    }

    fun onDestroy() {
        mPlaybackManager.handleStopRequest(null)
        mMediaNotificationManager?.stopNotification()
        mDelayedStopHandler.removeCallbacksAndMessages(null)
        mSession.release()
    }

    fun onPlayBackStart() {
        mSession.isActive = true
        mDelayedStopHandler.removeCallbacksAndMessages(null)
        service?.startService(Intent(service?.applicationContext, com.pietrantuono.podcasts.musicservice.MusicService::class.java))
    }

    fun onPlaybackStop() {
        mSession.isActive = false
        mDelayedStopHandler.removeCallbacksAndMessagesAndSendEmptyMessage()
        service?.stopForeground(true)
    }

    fun onNotificationRequired() {
        mMediaNotificationManager?.startNotification()
    }

    fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        mSession.setPlaybackState(newState)
    }

    fun stopSelf() {
        service?.stopSelf()
    }

    fun downloadAndAddToQueue(uri: String) {
        otherActions.downloadAndAddToQueue(uri, service)
    }

}
