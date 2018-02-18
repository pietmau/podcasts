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
    @Inject lateinit var presenter: MusicServicePresenter

    override fun onCreate() {
        super.onCreate()
        (application as App).appComponent?.with(MusicServiceModule(this))?.inject(this)
        presenter.service = this
        presenter.onCreate()
    }

    override fun onStartCommand(startIntent: Intent, flags: Int, startId: Int) = presenter.onStartCommand(startIntent, startId)

    override fun onDestroy() {
        presenter.onDestroy()
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?) = MediaBrowserServiceCompat.BrowserRoot(MEDIA_ID_EMPTY_ROOT, null)

    override fun onLoadChildren(parentMediaId: String,
                                result: MediaBrowserServiceCompat.Result<List<MediaItem>>) {
        result.sendResult(presenter.playlist)
    }

    override fun onPlaybackStart() {
        presenter.onPlayBackStart()
    }

    override fun onPlaybackStop() {
        presenter.onPlaybackStop()
    }

    override fun onNotificationRequired() {
        presenter.onNotificationRequired()
    }

    override fun onPlaybackStateUpdated(newState: PlaybackStateCompat) {
        presenter.onPlaybackStateUpdated(newState)
    }

    override fun downloadAndAddToQueue(uri: String) {
        presenter.downloadAndAddToQueue(uri)
    }
}
