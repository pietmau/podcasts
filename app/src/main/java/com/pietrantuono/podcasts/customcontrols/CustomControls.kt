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
package com.pietrantuono.podcasts.customcontrols

import android.content.ComponentName
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.RemoteException
import android.os.SystemClock
import android.support.v4.content.ContextCompat
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.text.format.DateUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.fullscreenplay.presenter.FullscreenPresenter
import com.pietrantuono.podcasts.player.player.service.PlayerService
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.AlbumArtCache
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class CustomControls(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    @BindView(R.id.prev) lateinit var skipPrev: ImageView
    @BindView(R.id.next) lateinit var skipNext: ImageView
    @BindView(R.id.play_pause) lateinit var playPause: ImageView
    @BindView(R.id.startText) lateinit var start: TextView
    @BindView(R.id.endText) lateinit var end: TextView
    @BindView(R.id.seekBar1) lateinit var seekbar: SeekBar
    @BindView(R.id.line1) lateinit var line1: TextView
    @BindView(R.id.line2) lateinit var line2: TextView
    @BindView(R.id.line3) lateinit var line3: TextView
    @BindView(R.id.progressBar1) lateinit var loading: ProgressBar
    @BindView(R.id.controllers) lateinit var controllers: View
    private val pauseDrawable: Drawable
    private val playDrawable: Drawable
    @BindView(R.id.background_image) lateinit var backgroundImage: ImageView
    private var currentArtUrl: String? = null
    private val aHandler = Handler()
    private var mediaBrowser: MediaBrowserCompat? = null
    private var supportMediaController: MediaControllerCompat? = null
    private val transportControls = supportMediaController?.transportControls

    private val connectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            try {
                connectToSession(mediaBrowser?.sessionToken)
            } catch (e: RemoteException) { }
        }
    }

    private val updateProgressTask = { updateProgress() }
    private val executorService = Executors.newSingleThreadScheduledExecutor()
    private var scheduleFuture: ScheduledFuture<*>? = null
    private var lastPlaybackState: PlaybackStateCompat? = null

    private val callback = object : MediaControllerCompat.Callback() {
        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            updatePlaybackState(state)
        }

        override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
            if (metadata != null) {
                updateMediaDescription(metadata.description)
                updateDuration(metadata)
            }
        }
    }

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(R.layout.custom_player, this)
        ButterKnife.bind(this)
        pauseDrawable = ContextCompat.getDrawable(getContext(), R.drawable.uamp_ic_pause_white_48dp)
        playDrawable = ContextCompat.getDrawable(getContext(), R.drawable.uamp_ic_play_arrow_white_48dp)
        skipNext.setOnClickListener {
            transportControls?.skipToNext()
        }
        skipPrev.setOnClickListener {
            transportControls?.skipToPrevious()
        }
        playPause.setOnClickListener {
            supportMediaController?.playbackState?.let {
                when (it.state) {
                    PlaybackStateCompat.STATE_PLAYING, PlaybackStateCompat.STATE_BUFFERING -> {
                        transportControls?.pause()
                        stopSeekbarUpdate()
                    }
                    PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED -> {
                        transportControls?.play()
                        scheduleSeekbarUpdate()
                    }
                }
            }
        }
        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                start.text = DateUtils.formatElapsedTime((progress / 1000).toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                stopSeekbarUpdate()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                transportControls?.seekTo(seekBar.progress.toLong())
                scheduleSeekbarUpdate()
            }
        })

        mediaBrowser = MediaBrowserCompat(getContext(), ComponentName(getContext(), PlayerService::class.java), connectionCallback, null)
    }


    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        val mediaController = MediaControllerCompat(context, token)
        if (mediaController.metadata == null) {
            return
        }
        supportMediaController = mediaController
        supportMediaController?.registerCallback(callback)
        val state = supportMediaController?.playbackState
        updatePlaybackState(state)
        val metadata = supportMediaController?.metadata
        if (metadata != null) {
            updateMediaDescription(metadata.description)
            updateDuration(metadata)
        }
        updateProgress()
        if (state?.state == PlaybackStateCompat.STATE_PLAYING || state?.state == PlaybackStateCompat.STATE_BUFFERING) {
            scheduleSeekbarUpdate()
        }
    }

    private fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        if (!executorService.isShutdown) {
            scheduleFuture = executorService.scheduleAtFixedRate(
                    { aHandler.post(updateProgressTask) }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
        }
    }

    private fun stopSeekbarUpdate() {
        scheduleFuture?.cancel(false)
    }

    fun onStart() {
        mediaBrowser?.connect()
    }

    fun onStop() {
        mediaBrowser?.disconnect()
        supportMediaController?.unregisterCallback(callback)
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        executorService.shutdown()
    }

    private fun fetchImageAsync(description: MediaDescriptionCompat) {
        if (description.iconUri == null) {
            return
        }
        val artUrl = description.iconUri?.toString()
        currentArtUrl = artUrl
        val cache = AlbumArtCache.getInstance()
        var art: Bitmap? = cache.getBigImage(artUrl)
        if (art == null) {
            art = description.iconBitmap
        }
        if (art != null) {
            // if we have the art cached or from the MediaDescription, use it:
            backgroundImage.setImageBitmap(art)
        } else {
            // otherwise, fetch a high res version and update:
            cache.fetch(artUrl) { artUrl, bitmap, icon ->
                // sanity check, in case a new fetch request has been done while
                // the previous hasn't yet returned:
                if (artUrl == currentArtUrl) {
                    backgroundImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun updateMediaDescription(description: MediaDescriptionCompat?) {
        if (description == null) {
            return
        }
        line1.text = description.title
        line2.text = description.subtitle
        fetchImageAsync(description)
    }

    private fun updateDuration(metadata: MediaMetadataCompat?) {
        if (metadata == null) {
            return
        }
        val duration = metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt()
        seekbar.max = duration
        end.text = DateUtils.formatElapsedTime((duration / 1000).toLong())
    }

    private fun updatePlaybackState(state: PlaybackStateCompat?) {
        if (state == null) {
            return
        }
        lastPlaybackState = state
        if (supportMediaController != null && supportMediaController?.extras != null) {
            val castName: String? = supportMediaController?.extras?.getString(EXTRA_CONNECTED_CAST)
            val line3Text = if (castName == null)
                ""
            else
                resources
                        .getString(R.string.casting_to_device, castName)
            line3.text = line3Text
        }

        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> {
                loading.visibility = View.INVISIBLE
                playPause.visibility = View.VISIBLE
                playPause.setImageDrawable(pauseDrawable)
                controllers.visibility = View.VISIBLE
                scheduleSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                controllers.visibility = View.VISIBLE
                loading.visibility = View.INVISIBLE
                playPause.visibility = View.VISIBLE
                playPause.setImageDrawable(playDrawable)
                stopSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                loading.visibility = View.INVISIBLE
                playPause.visibility = View.VISIBLE
                playPause.setImageDrawable(playDrawable)
                stopSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_BUFFERING -> {
                playPause.visibility = View.INVISIBLE
                loading.visibility = View.VISIBLE
                line3.setText(R.string.loading)
                stopSeekbarUpdate()
            }
        }

        skipNext.visibility = if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_NEXT == 0L)
            View.INVISIBLE
        else
            View.VISIBLE
        skipPrev.visibility = if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS == 0L)
            View.INVISIBLE
        else
            View.VISIBLE
    }

    private fun updateProgress() {
        lastPlaybackState?.let {
            var currentPosition = it.position
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                val timeDelta = SystemClock.elapsedRealtime() - it.lastPositionUpdateTime
                currentPosition += (timeDelta.toInt() * it.playbackSpeed).toLong()
            }
            seekbar.progress = currentPosition.toInt()
        }
    }

    companion object {
        val EXTRA_CONNECTED_CAST = "com.example.android.uamp.CAST_NAME"
        private val PROGRESS_UPDATE_INTERNAL: Long = 1000
        private val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 100
    }

    fun setBackgroundColors(backgroundColor: Int) {

    }

    fun setCallback(presenter: FullscreenPresenter) {

    }
}
