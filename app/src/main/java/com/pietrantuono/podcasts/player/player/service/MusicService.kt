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

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaButtonReceiver
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.player.player.service.di.ServiceModule
import com.pietrantuono.podcasts.player.player.service.playbackmanager.PlaybackManager
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificationManager
import com.pietrantuono.podcasts.player.player.service.queue.QueueManager
import javax.inject.Inject

class MusicService() : MediaBrowserServiceCompat(), PlaybackManager.PlaybackServiceCallback, QueueManager.MetadataUpdateListener {
    @Inject lateinit var playbackManager: PlaybackManager
    @Inject lateinit var session: MediaSessionCompat
    @Inject lateinit var mediaNotificationManager: NotificationManager
    @Inject lateinit var delayedStopHandler: DelayedStopHandler

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent!!.with(ServiceModule(this)).inject(this)
        setSessionToken(session.sessionToken)
        playbackManager.updatePlaybackState(null)
    }

    override fun onStartCommand(startIntent: Intent?, flags: Int, startId: Int): Int {
        if (startIntent != null) {
            val command = startIntent.getStringExtra(CMD_NAME)
            if (ACTION_CMD == startIntent.action) {
                if (CMD_PAUSE == command) {
                    playbackManager.handlePauseRequest()
                }
            } else {
                MediaButtonReceiver.handleIntent(session, startIntent)
            }
        }
        delayedStopHandler.removeCallbacksAndMessages(null)
        delayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
        return Service.START_STICKY
    }

    override fun onDestroy() {
        playbackManager.handleStopRequest(null)
        mediaNotificationManager.stopNotification()
        delayedStopHandler.removeCallbacksAndMessages(null)
        session.release()
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
        session.isActive = true
        delayedStopHandler.removeCallbacksAndMessages(null)
        startService(Intent(applicationContext, MusicService::class.java))
    }

    override fun onPlaybackStop() {
        session.isActive = false
        delayedStopHandler.removeCallbacksAndMessages(null)
        delayedStopHandler.sendEmptyMessageDelayed(0, STOP_DELAY.toLong())
        stopForeground(true)
    }

    override fun onNotificationRequired() {
        mediaNotificationManager.startNotification()
    }

    override fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        session.setPlaybackState(newState)
    }

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        session.setMetadata(metadata)
    }

    override fun onMetadataRetrieveError() {
        playbackManager.updatePlaybackState(getString(R.string.error_no_metadata))
    }

    override fun onCurrentQueueIndexUpdated(queueIndex: Int) {
        playbackManager.handlePlayRequest()
    }

    override fun onQueueUpdated(title: String?, newQueue: List<MediaSessionCompat.QueueItem?>?) {
        session.setQueue(newQueue)
        session.setQueueTitle(title)
    }


    companion object {
        val EXTRA_CONNECTED_CAST = "com.pietrantuono.podcasts.CAST_NAME"
        val ACTION_CMD = "com.pietrantuono.podcasts.ACTION_CMD"
        val CMD_NAME = "CMD_NAME"
        val CMD_PAUSE = "CMD_PAUSE"
        val CMD_STOP_CASTING = "CMD_STOP_CASTING"
        private val STOP_DELAY = 30000
        private val MEDIA_ID_ROOT: String = "ROOT"
    }
}
