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

import android.app.Notification
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.RemoteException
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.support.v7.app.NotificationCompat
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.FullscreenPlayActivity
import com.pietrantuono.podcasts.player.player.LogHelper
import com.pietrantuono.podcasts.player.player.service.MusicService
import com.pietrantuono.podcasts.player.player.service.ResourceHelper


class Notificator @Throws(RemoteException::class)
constructor(private val service: MusicService, private val iconCache: IconCache) : BroadcastReceiver() {
    private var mSessionToken: MediaSessionCompat.Token? = null
    private var mController: MediaControllerCompat? = null
    private var transportControls: MediaControllerCompat.TransportControls? = null
    private var mPlaybackState: PlaybackStateCompat? = null
    private var mMetadata: MediaMetadataCompat? = null
    private val mNotificationManager: NotificationManagerCompat
    private val mPauseIntent: PendingIntent
    private val mPlayIntent: PendingIntent
    private val mPreviousIntent: PendingIntent
    private val mNextIntent: PendingIntent
    private val mStopCastIntent: PendingIntent
    private val mNotificationColor: Int
    private var mStarted = false

    init {
        updateSessionToken()
        mNotificationColor = ResourceHelper.getThemeColor(service, R.attr.colorPrimary,
                Color.DKGRAY)
        mNotificationManager = NotificationManagerCompat.from(service)
        val pkg = service.packageName
        mPauseIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                Intent(ACTION_PAUSE).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPlayIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                Intent(ACTION_PLAY).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mPreviousIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                Intent(ACTION_PREV).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mNextIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                Intent(ACTION_NEXT).setPackage(pkg), PendingIntent.FLAG_CANCEL_CURRENT)
        mStopCastIntent = PendingIntent.getBroadcast(service, REQUEST_CODE,
                Intent(ACTION_STOP_CASTING).setPackage(pkg),
                PendingIntent.FLAG_CANCEL_CURRENT)
        mNotificationManager.cancelAll()
    }

    fun startNotification() {
        if (!mStarted) {
            mMetadata = mController!!.metadata
            mPlaybackState = mController!!.playbackState
            val notification = createNotification()
            if (notification != null) {
                mController!!.registerCallback(mediaControllerCompatCallback)
                val filter = IntentFilter()
                //filter.addAction(ACTION_NEXT)
                filter.addAction(ACTION_PAUSE)
                filter.addAction(ACTION_PLAY)
                //filter.addAction(ACTION_PREV)
                //filter.addAction(ACTION_STOP_CASTING)
                service.registerReceiver(this, filter)
                service.startForeground(NOTIFICATION_ID, notification)
                mStarted = true
            }
        }
    }

    /**
     * Removes the notification and stops tracking the session. If the session
     * was destroyed this has no effect.
     */
    fun stopNotification() {
        if (mStarted) {
            mStarted = false
            mController?.unregisterCallback(mediaControllerCompatCallback)
            try {
                mNotificationManager.cancel(NOTIFICATION_ID)
                service.unregisterReceiver(this)
            } catch (ex: IllegalArgumentException) {
                // ignore if the receiver is not registered.
            }
            service.stopForeground(true)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.action) {
            ACTION_PAUSE -> transportControls?.pause()
            ACTION_PLAY -> transportControls?.play()
            ACTION_NEXT -> transportControls?.skipToNext()
            ACTION_PREV -> transportControls?.skipToPrevious()
            ACTION_STOP_CASTING -> {
                val i = Intent(context, MusicService::class.java)
                i.action = MusicService.ACTION_CMD
                i.putExtra(MusicService.CMD_NAME, MusicService.CMD_STOP_CASTING)
                service.startService(i)
            }
            else -> {
            }
        }
    }

    /**
     * Update the state based on a change on the session token. Called either when
     * we are running for the first time or when the media session owner has destroyed the session
     * (see [android.media.session.MediaController.Callback.onSessionDestroyed])
     */
    @Throws(RemoteException::class)
    private fun updateSessionToken() {
        val freshToken = service.sessionToken
        if (mSessionToken == null && freshToken != null || mSessionToken != null && mSessionToken != freshToken) {
            if (mController != null) {
                mController!!.unregisterCallback(mediaControllerCompatCallback)
            }
            mSessionToken = freshToken
            if (mSessionToken != null) {
                mController = MediaControllerCompat(service, mSessionToken!!)
                transportControls = mController!!.transportControls
                if (mStarted) {
                    mController!!.registerCallback(mediaControllerCompatCallback)
                }
            }
        }
    }

    private fun createContentIntent(description: MediaDescriptionCompat?): PendingIntent {
        val openUI = Intent(service, FullscreenPlayActivity::class.java)
        openUI.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        //openUI.putExtra(MusicPlayerActivity.EXTRA_START_FULLSCREEN, true)
        if (description != null) {
            openUI.putExtra(FullscreenPlayActivity.EXTRA_CURRENT_MEDIA_DESCRIPTION, description)
        }
        return PendingIntent.getActivity(service, REQUEST_CODE, openUI,
                PendingIntent.FLAG_CANCEL_CURRENT)
    }

    private val mediaControllerCompatCallback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            mPlaybackState = state
            if (state.state == PlaybackStateCompat.STATE_STOPPED || state.state == PlaybackStateCompat.STATE_NONE) {
                stopNotification()
            } else {
                val notification = createNotification()
                if (notification != null) {
                    mNotificationManager.notify(NOTIFICATION_ID, notification)
                }
            }
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            mMetadata = metadata
            val notification = createNotification()
            if (notification != null) {
                mNotificationManager.notify(NOTIFICATION_ID, notification)
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

    private fun createNotification(): Notification? {
        if (mMetadata == null || mPlaybackState == null) {
            return null
        }
        val notificationBuilder = NotificationCompat.Builder(service)
        var playPauseButtonPosition = 0

        // If skip to previous action is enabled
//        if (mPlaybackState!!.actions and PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS != 0L) {
//            notificationBuilder.addAction(R.drawable.ic_skip_previous_white_24dp,
//                    service.getString(R.string.label_previous), mPreviousIntent)
//
//            // If there is a "skip to previous" button, the play/pause button will
//            // be the second one. We need to keep track of it, because the MediaStyle notification
//            // requires to specify the index of the buttons (actions) that should be visible
//            // when in compact view.
//            playPauseButtonPosition = 1
//        }
//
        addPlayPauseAction(notificationBuilder)
//
//        // If skip to next action is enabled
//        if (mPlaybackState!!.actions and PlaybackStateCompat.ACTION_SKIP_TO_NEXT != 0L) {
//            notificationBuilder.addAction(R.drawable.ic_skip_next_white_24dp,
//                    service.getString(R.string.label_next), mNextIntent)
//        }
        val description = mMetadata!!.description
        notificationBuilder
                .setStyle(NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(
                                *intArrayOf(playPauseButtonPosition))  // show only play/pause in compact view
                        .setMediaSession(mSessionToken))
                .setColor(mNotificationColor)
                .setSmallIcon(R.drawable.ic_notification)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setUsesChronometer(true)
                .setContentIntent(createContentIntent(description))
                .setContentTitle(description.title)
                .setContentText(description.subtitle)

        if (mController != null && mController!!.extras != null) {
            val castName = mController!!.extras.getString(MusicService.EXTRA_CONNECTED_CAST)
            if (castName != null) {
                val castInfo = service.resources
                        .getString(R.string.casting_to_device, castName)
                notificationBuilder.setSubText(castInfo)
                notificationBuilder.addAction(R.drawable.ic_close_black_24dp,
                        service.getString(R.string.stop_casting), mStopCastIntent)
            }
        }
        setNotificationPlaybackState(notificationBuilder)
        iconCache.setOrGetIcon(mNotificationManager, description, notificationBuilder)
        return notificationBuilder.build()
    }

    private fun addPlayPauseAction(builder: NotificationCompat.Builder) {
        val label: String
        val icon: Int
        val intent: PendingIntent
        if (mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING) {
            label = service.getString(R.string.label_pause)
            icon = R.drawable.uamp_ic_pause_white_24dp
            intent = mPauseIntent
        } else {
            label = service.getString(R.string.label_play)
            icon = R.drawable.uamp_ic_play_arrow_white_24dp
            intent = mPlayIntent
        }
        builder.addAction(android.support.v4.app.NotificationCompat.Action(icon, label, intent))
    }

    private fun setNotificationPlaybackState(builder: NotificationCompat.Builder) {
        if (mPlaybackState == null || !mStarted) {
            service.stopForeground(true)
            return
        }
        if (mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING && mPlaybackState!!.position >= 0) {
            builder
                    .setWhen(System.currentTimeMillis() - mPlaybackState!!.position)
                    .setShowWhen(true)
                    .setUsesChronometer(true)
        } else {
            builder
                    .setWhen(0)
                    .setShowWhen(false)
                    .setUsesChronometer(false)
        }
        builder.setOngoing(mPlaybackState!!.state == PlaybackStateCompat.STATE_PLAYING)
    }

    companion object {
        private val TAG = LogHelper.makeLogTag(Notificator::class.java)
        val NOTIFICATION_ID = 412
        private val REQUEST_CODE = 100
        val ACTION_PAUSE = "com.pietrantuono.podcasts.pause"
        val ACTION_PLAY = "com.pietrantuono.podcasts.play"
        val ACTION_PREV = "com.pietrantuono.podcasts.prev"
        val ACTION_NEXT = "com.pietrantuono.podcasts.next"
        val ACTION_STOP_CASTING = "com.pietrantuono.podcasts.stop_cast"
    }
}
