package com.pietrantuono.podcasts.player.player.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.IBinder
import android.util.Log
import android.view.View
import com.google.android.exoplayer2.source.MediaSource
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener
import com.pietrantuono.podcasts.addpodcast.singlepodcast.dagger.SinglePodcastModule
import com.pietrantuono.podcasts.apis.Episode
import com.pietrantuono.podcasts.application.App
import com.pietrantuono.podcasts.application.DebugLogger
import com.pietrantuono.podcasts.player.player.PodcastFeedSource
import com.pietrantuono.podcasts.player.player.playback.PlaybackWrapper
import com.pietrantuono.podcasts.player.player.player.Player
import com.pietrantuono.podcasts.player.player.player.SimpleExoPlayerEventListener
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.NotificatorService
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificator
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.PlaybackNotificatorImpl
import javax.inject.Inject

class PlayerService() : InstrumentedService(), Player, NotificatorService {
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

    override fun playFeed(source: PodcastFeedSource) {
        //playback.playAll(creator.createConcatenateMediaSource(source))
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
        (applicationContext as App).applicationComponent?.with(SinglePodcastModule())?.inject(this)
        logger.debug(TAG, "onCreate")
        broadcastManager.registerForBroadcastsFromNotification(this, receiver)
        playback.addListener(exoPlayerEventListener)
    }

    override fun onBind(intent: Intent?): IBinder? {
        logger.debug(TAG, "onBind")
        checkIfShoudBeForeground()
        return PlayerServiceBinder(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        logger.debug(TAG, "onStartCommand")
        checkIfShoudBeForeground()
        return START_STICKY
    }

    override fun onUnbind(intent: Intent?): Boolean {
        logger.debug(TAG, "onUnbind")
        checkIfShoudBeForeground()
        return true
    }

    override fun onRebind(intent: Intent?) {
        logger.debug(TAG, "onRebind")
        checkIfShoudBeForeground()
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

