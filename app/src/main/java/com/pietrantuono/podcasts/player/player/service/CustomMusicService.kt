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
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.player.player.LogHelper
import com.pietrantuono.podcasts.player.player.service.di.ServiceModule
import com.pietrantuono.podcasts.player.player.service.playback.Playback
import com.pietrantuono.podcasts.player.player.service.playbackmanager.CustomPlaybackManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.CustomNotificationManager
import com.pietrantuono.podcasts.player.player.service.provider.PodcastProvider
import com.pietrantuono.podcasts.repository.EpisodesRepository
import java.lang.ref.WeakReference
import javax.inject.Inject

class CustomMusicService() : MediaBrowserServiceCompat(), CustomPlaybackManager.PlaybackServiceCallback {
    private val MEDIA_ID_ROOT: String = "ROOT"
    private var mPlaybackManager: CustomPlaybackManager? = null
    private var mSession: MediaSessionCompat? = null
    private var mMediaNotificationManager: CustomNotificationManager? = null
    private var mSessionExtras: Bundle? = null
    private val mDelayedStopHandler = DelayedStopHandler(this)
    @Inject lateinit var repository: EpisodesRepository
    @Inject lateinit var provider: PodcastProvider
    private var manager: PodcastManager? = null
    @Inject lateinit var playback: Playback

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(ServiceModule())?.inject(this)
        manager = PodcasteManagerImpl(repository, provider, object : PodcasteManagerImpl.MetadataUpdateListener {
            override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
                mSession?.setMetadata(metadata)
            }

            override fun onMetadataRetrieveError() {
                mPlaybackManager?.updatePlaybackState(getString(R.string.error_no_metadata))
            }

            override fun onCurrentQueueIndexUpdated(queueIndex: Int) {
                mPlaybackManager?.handlePlayRequest()
            }

            override fun onQueueUpdated(title: String?, newQueue: List<MediaSessionCompat.QueueItem?>?) {
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
        val intent = Intent(context, FullscreenPlayActivity::class.java)
        val pi = PendingIntent.getActivity(context, 99 /*request code*/,
                intent, PendingIntent.FLAG_UPDATE_CURRENT)
        mSession?.setSessionActivity(pi)
        mSessionExtras = Bundle()
        mSession?.setExtras(mSessionExtras)
        mPlaybackManager?.updatePlaybackState(null)
        try {
            mMediaNotificationManager = CustomNotificationManager(this)
        } catch (e: RemoteException) {
            throw IllegalStateException("Could not create a MediaNotificationManager", e)
        }
    }

    override fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        if (startIntent != null) {
            val action = startIntent.action
            val command = startIntent.getStringExtra(CMD_NAME)
            if (ACTION_CMD == action) {
                if (CMD_PAUSE == command) {
                    mPlaybackManager?.handlePauseRequest()
                } else if (CMD_STOP_CASTING == command) {
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
                    return
                }
                service.stopSelf()
            }
        }
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(CustomMusicService::class.java)
        val EXTRA_CONNECTED_CAST = "com.example.android.uamp.CAST_NAME"
        val ACTION_CMD = "com.example.android.uamp.ACTION_CMD"
        val CMD_NAME = "CMD_NAME"
        val CMD_PAUSE = "CMD_PAUSE"
        val CMD_STOP_CASTING = "CMD_STOP_CASTING"
        private val STOP_DELAY = 30000
    }
}
