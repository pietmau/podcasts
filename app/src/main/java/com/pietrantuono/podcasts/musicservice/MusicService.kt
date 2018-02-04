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

package com.pietrantuono.podcasts.musicservice

import android.content.Intent
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.PlaybackStateCompat
import com.pietrantuono.podcasts.application.App
import player.CustomMediaService
import player.playback.PlaybackServiceCallback
import player.utils.MediaIDHelper.MEDIA_ID_EMPTY_ROOT
import javax.inject.Inject

class MusicService : MediaBrowserServiceCompat(), CustomMediaService, PlaybackServiceCallback {
    @Inject lateinit var musicServicePresenter: MusicServicePresenter

    override fun onCreate() {
        super.onCreate()
        (application as App).appComponent?.with(MusicServiceModule(this))?.inject(this)
        musicServicePresenter.service = this
        musicServicePresenter.onCreate()
    }

    override fun onStartCommand(startIntent: Intent, flags: Int, startId: Int): Int {
        return musicServicePresenter.onStartCommand(startIntent, startId)
    }

    override fun onDestroy() {
        musicServicePresenter.onDestroy()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int,
                           rootHints: Bundle?): MediaBrowserServiceCompat.BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot(MEDIA_ID_EMPTY_ROOT, null)
    }

    override fun onLoadChildren(parentMediaId: String,
                                result: MediaBrowserServiceCompat.Result<List<MediaItem>>) {
        result.sendResult(musicServicePresenter.playlist)
    }

    override fun onPlaybackStart() {
        musicServicePresenter.onPlayBackStart()
    }

    override fun onPlaybackStop() {
        musicServicePresenter.onPlaybackStop()
    }

    override fun onNotificationRequired() {
        musicServicePresenter.onNotificationRequired()
    }

    override fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        musicServicePresenter.onPlaybackStateUpdated(newState)
    }

    override fun downloadAndAddToQueue(uri: String) {
        musicServicePresenter.downloadAndAddToQueue(uri)
    }
}
