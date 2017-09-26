/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.pietrantuono.podcasts.player.player.service

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.os.RemoteException
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaButtonReceiver
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.media.MediaRouter
import com.example.android.uamp.MediaNotificationManager
import com.example.android.uamp.R
import com.example.android.uamp.playback.QueueManager
import com.example.android.uamp.ui.NowPlayingActivity
import com.example.android.uamp.utils.CarHelper
import com.example.android.uamp.utils.LogHelper
import com.example.android.uamp.utils.MediaIDHelper.MEDIA_ID_ROOT
import com.example.android.uamp.utils.WearHelper
import com.google.android.gms.cast.framework.CastContext
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.player.player.service.di.ServiceModule
import com.pietrantuono.podcasts.repository.EpisodesRepository
import java.lang.ref.WeakReference
import javax.inject.Inject

class CustomMusicService() : MediaBrowserServiceCompat(), CustomPlaybackManager.PlaybackServiceCallback {
    private var mPlaybackManager: CustomPlaybackManager? = null
    private var mSession: MediaSessionCompat? = null
    private var mMediaNotificationManager: MediaNotificationManager? = null
    private var mSessionExtras: Bundle? = null
    private val mDelayedStopHandler = DelayedStopHandler(this)
    private var mMediaRouter: MediaRouter? = null

    @Inject lateinit var repository: EpisodesRepository

    @Inject lateinit var provider: PodcastProvider

    private var manager: PodcastManager? = null

    override fun onCreate() {
        super.onCreate()
        LogHelper.d(TAG, "onCreate")
        (applicationContext as App).applicationComponent?.with(ServiceModule())?.inject(this)
        val playback = CustomLocalPlayback(this, provider)
        manager = PodcasteManagerImpl(repository,provider ,object : QueueManager.MetadataUpdateListener {
            override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                mSession?.setMetadata(metadata)
            }

            override fun onMetadataRetrieveError() {
                mPlaybackManager?.updatePlaybackState(getString(R.string.error_no_metadata))
            }

            override fun onCurrentQueueIndexUpdated(queueIndex: Int) {
                mPlaybackManager?.handlePlayRequest()
            }

            override fun onQueueUpdated(title: String?, newQueue: MutableList<MediaSessionCompat.QueueItem>?) {
                mSession?.setQueue(newQueue)
                mSession?.setQueueTitle(title)
            }
        })
        mPlaybackManager = CustomPlaybackManager(this, resources, manager, playback)
        mSession = MediaSessionCompat(this, "OtherMusicService")
        setSessionToken(mSession?.sessionToken)
        mSession?.setCallback(mPlaybackManager?.mediaSessionCallback)
        mSession?.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)

        val context = applicationContext
        val intent = Intent(context, NowPlayingActivity::class.java)
        val pi = PendingIntent.getActivity(context, 99 /*request code*/,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mSession?.setSessionActivity(pi)

        mSessionExtras = Bundle()
        CarHelper.setSlotReservationFlags(mSessionExtras, true, true, true)
        WearHelper.setSlotReservationFlags(mSessionExtras, true, true)
        WearHelper.setUseBackgroundFromTheme(mSessionExtras, true)
        mSession?.setExtras(mSessionExtras)

        mPlaybackManager?.updatePlaybackState(null)

        try {
            //mMediaNotificationManager = MediaNotificationManager(this)
        } catch (e: RemoteException) {
            throw IllegalStateException("Could not create a MediaNotificationManager", e)
        }
        mMediaRouter = MediaRouter.getInstance(applicationContext)
    }

    override fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        if (startIntent != null) {
            val action = startIntent.action
            val command = startIntent.getStringExtra(CMD_NAME)
            if (ACTION_CMD == action) {
                if (CMD_PAUSE == command) {
                    mPlaybackManager?.handlePauseRequest()
                } else if (CMD_STOP_CASTING == command) {
                    CastContext.getSharedInstance(this).sessionManager.endCurrentSession(true)
                }
            } else {
                MediaButtonReceiver.handleIntent(mSession, startIntent)
            }
        }
        mDelayedStopHandler.removeCallbacksAndMessages(null)
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
        return Service.START_STICKY
    }

    override fun onDestroy() {
        LogHelper.d(TAG, "onDestroy")
        mPlaybackManager?.handleStopRequest(null)
        mMediaNotificationManager?.stopNotification()

        mDelayedStopHandler.removeCallbacksAndMessages(null)
        mSession?.release()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot(MEDIA_ID_ROOT, null)
    }

    override fun onLoadChildren(parentMediaId: String,
                                result: MediaBrowserServiceCompat.Result<List<MediaItem>>) {
        throw UnsupportedOperationException("Not implemented")
    }

    override fun onPlaybackStart() {
        mSession?.isActive = true

        mDelayedStopHandler.removeCallbacksAndMessages(null)
        startService(Intent(applicationContext, CustomMusicService::class.java))
    }

    override fun onPlaybackStop() {
        mSession?.isActive = false
        mDelayedStopHandler.removeCallbacksAndMessages(null)
        mDelayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
        stopForeground(true)
    }

    override fun onNotificationRequired() {
        mMediaNotificationManager?.startNotification()
    }

    override fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        mSession?.setPlaybackState(newState)
    }

    private class DelayedStopHandler(service: CustomMusicService) : Handler() {
        private val mWeakReference: WeakReference<CustomMusicService>

        init {
            mWeakReference = WeakReference(service)
        }

        override fun handleMessage(msg: Message) {
            val service = mWeakReference.get()
            if (service != null && service.mPlaybackManager?.playback != null) {
                if (service?.mPlaybackManager?.playback?.isPlaying == true) {
                    LogHelper.d(TAG, "Ignoring delayed stop since the media player is in use.")
                    return
                }
                LogHelper.d(TAG, "Stopping service with delay handler.")
                service.stopSelf()
            }
        }
    }

    companion object {

        private val TAG = LogHelper.makeLogTag(CustomMusicService::class.java)

        // Extra on MediaSession that contains the Cast device name currently connected to
        val EXTRA_CONNECTED_CAST = "com.example.android.uamp.CAST_NAME"
        // The action of the incoming Intent indicating that it contains a command
        // to be executed (see {@link #onStartCommand})
        val ACTION_CMD = "com.example.android.uamp.ACTION_CMD"
        // The key in the extras of the incoming Intent indicating the command that
        // should be executed (see {@link #onStartCommand})
        val CMD_NAME = "CMD_NAME"
        // A value of a CMD_NAME key in the extras of the incoming Intent that
        // indicates that the music playback should be paused (see {@link #onStartCommand})
        val CMD_PAUSE = "CMD_PAUSE"
        // A value of a CMD_NAME key that indicates that the music playback should switch
        // to local playback from cast playback.
        val CMD_STOP_CASTING = "CMD_STOP_CASTING"
        // Delay stopSelf by using a handler.
        private val STOP_DELAY = 30000
    }
}
