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

package com.pietrantuono.podcasts.player.player.service.playbacknotificator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.RemoteException
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.player.player.service.MusicService

class Notificator @Throws(RemoteException::class)

constructor(
        private val service: MusicService,
        private val creator: NotificationCreator,
        private val notificationManager: NotificationManagerCompat)
    : BroadcastReceiver() {

    private var sessionToken: MediaSessionCompat.Token? = null
    private var controller: MediaControllerCompat? = null
    private var playbackState: PlaybackStateCompat? = null
    private var metadata: MediaMetadataCompat? = null
    private var started = false

    init {
        updateSessionToken()
    }

    fun startNotification() {
        if (!started) {
            metadata = controller?.metadata
            playbackState = controller?.playbackState
            creator.createNotification(metadata, playbackState, sessionToken, started)?.let {
                controller?.registerCallback(mediaControllerCompatCallback)
                service.registerReceiver(this, PlayPauseIntentFilter())
                service.startForeground(NotificationsConstants.NOTIFICATION_ID, it)
                started = true
            }
        }
    }

    fun stopNotification() {
        if (started) {
            started = false
            controller?.unregisterCallback(mediaControllerCompatCallback)
            try {
                notificationManager.cancel(NotificationsConstants.NOTIFICATION_ID)
                service.unregisterReceiver(this)
            } catch (ex: IllegalArgumentException) {
            }
            service.stopForeground(true)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            NotificationsConstants.ACTION_PAUSE -> controller?.transportControls?.pause()
            NotificationsConstants.ACTION_PLAY -> controller?.transportControls?.play()
            NotificationsConstants.ACTION_NEXT -> controller?.transportControls?.skipToNext()
            NotificationsConstants.ACTION_PREV -> controller?.transportControls?.skipToPrevious()
        }
    }

    @Throws(RemoteException::class)
    private fun updateSessionToken() {
        val freshToken = service.sessionToken
        if (sessionToken == null && freshToken != null || sessionToken != null && sessionToken != freshToken) {
            controller?.unregisterCallback(mediaControllerCompatCallback)
            sessionToken = freshToken
            sessionToken?.let {
                controller = MediaControllerCompat(service, it)
                if (started) {
                    controller!!.registerCallback(mediaControllerCompatCallback)
                }
            }
        }
    }

    private val mediaControllerCompatCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            playbackState = state
            if (state.state == PlaybackStateCompat.STATE_STOPPED || state.state == PlaybackStateCompat.STATE_NONE) {
                stopNotification()
            } else {
                creator.createNotification(metadata, playbackState, sessionToken, started)?.let {
                    notificationManager.notify(NotificationsConstants.NOTIFICATION_ID, it)
                }

            }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            this@Notificator.metadata = metadata
            creator.createNotification(this@Notificator.metadata, playbackState, sessionToken, started)?.let {
                notificationManager.notify(NotificationsConstants.NOTIFICATION_ID, it)
            }
        }

        override fun onSessionDestroyed() {
            super.onSessionDestroyed()
            try {
                updateSessionToken()
            } catch (e: RemoteException) {
            }

        }
    }
}

