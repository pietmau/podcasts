package com.pietrantuono.podcasts.player.player.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserServiceCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.source.MediaSource
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.playback.PlaybackWrapper
import com.pietrantuono.podcasts.player.player.player.Player
import com.pietrantuono.podcasts.player.player.player.SimpleExoPlayerEventListener
import com.pietrantuono.podcasts.player.player.service.di.ServiceModule
import com.pietrantuono.podcasts.player.player.service.model.PlayerServiceModel
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificatorService
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificatorImpl
import javax.inject.Inject

class PlayerService() : Player, NotificatorService, MediaBrowserServiceCompat() {
    val TAG: String = "PlayerService"
    val ROOT = "ROOT"
    private var artwork: Bitmap? = null
    override var boundToFullScreen: Boolean = false
        set(value) {
            field = value
            checkIfShoudBeForeground()
        }
    @Inject lateinit var playback: PlaybackWrapper
    @Inject lateinit var logger: DebugLogger
    @Inject lateinit var notificator: PlaybackNotificator
    @Inject lateinit var broadcastManager: BroadcastManager
    @Inject lateinit var model: PlayerServiceModel

    private val callback = object : MediaSessionCompat.Callback() {
        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            this@PlayerService.onPlayFromMediaId(mediaId, extras)
        }
    }

    private fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
        if (model.currentlyPlayingEpisode?.link.equals(mediaId)) return
        else {
            model.getEpisodeByUrl(mediaId)
            playback.episode = model.currentlyPlayingEpisode
            playback.play()
        }
    }

    override fun onLoadChildren(parentId: String, result: Result<MutableList<MediaBrowserCompat.MediaItem>>) {
        throw UnsupportedOperationException("Browsing unsupported")
    }

    override fun onGetRoot(clientPackageName: String, clientUid: Int, rootHints: Bundle?): BrowserRoot? {
        return MediaBrowserServiceCompat.BrowserRoot(ROOT, null)
    }

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            broadcastManager.onReceive(intent, this@PlayerService)
        }
    }

    val exoPlayerEventListener: SimpleExoPlayerEventListener = object : SimpleExoPlayerEventListener() {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            Log.d("EventListener", "onPlayerStateChanged " + playWhenReady + " " + playbackState)
            updateNotification(playWhenReady, artwork)
        }
    }

    override fun setEpisode(episode: Episode) {
        playback.episode = episode
        artwork = null
        notificator.loadImage(episode.imageUrl, object : SimpleImageLoadingListener() {
            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                this@PlayerService.artwork = loadedImage
                checkIfShoudBeForeground()
            }
        })
    }

    override fun onCreate() {
        super.onCreate()
        (applicationContext as App).applicationComponent?.with(ServiceModule())?.inject(this)
        logger.debug(TAG, "onCreate")
        broadcastManager.registerForBroadcastsFromNotification(this, receiver)
        playback.addListener(exoPlayerEventListener)
        val mediaSession = MediaSessionCompat(this, "fubar")
        mediaSession?.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS)
        val stateBuilder = PlaybackStateCompat.Builder().setActions(PlaybackStateCompat.ACTION_PLAY or PlaybackStateCompat.ACTION_PLAY_PAUSE)
        mediaSession?.setPlaybackState(stateBuilder?.build())
        mediaSession?.setCallback(callback)
        setSessionToken(mediaSession?.getSessionToken())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug(TAG, "onStartCommand")
        checkIfShoudBeForeground()
        return START_STICKY
    }

    override fun onDestroy() {
        checkIfShoudBeForeground()
        logger.debug(TAG, "onDestroy")
        broadcastManager.unregisterForBroadcastsFromNotification(this, receiver)
    }

    override fun onTrimMemory(level: Int) {
        logger.debug(TAG, "onTrimMemory")
    }

    override fun playEpisode(episode: MediaSource) {
        //playback.playMediaSource(episode)
    }

    override fun pause() {
        playback.pause()
    }

    override fun play() {
        playback.play()
    }

    override fun stop() {
        playback.stop()
        stopForeground(PlaybackNotificatorImpl.REMOVE_NOTIFICATION)
        stopSelf()
    }

    override fun checkIfShoudBeForeground() {
        notificator.checkIfShoudBeForeground(this, playback.media, playback.playbackState, artwork)
    }

    private fun updateNotification(playWhenReady: Boolean, bitmap: Bitmap?) {
        notificator.updateNotification(this, this, playback.media, playback.playbackState, playWhenReady, artwork)
    }
}

