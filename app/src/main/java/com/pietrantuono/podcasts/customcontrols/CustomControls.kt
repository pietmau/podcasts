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
import com.pietrantuono.podcasts.R
import com.pietrantuono.podcasts.player.player.service.PlayerService
import com.pietrantuono.podcasts.player.player.service.playbacknotificator.AlbumArtCache
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

class CustomControls(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {
    private val mSkipPrev: ImageView
    private val mSkipNext: ImageView
    private val mPlayPause: ImageView
    private val mStart: TextView
    private val mEnd: TextView
    private val mSeekbar: SeekBar
    private val mLine1: TextView
    private val mLine2: TextView
    private val mLine3: TextView
    private val mLoading: ProgressBar
    private val mControllers: View
    private val mPauseDrawable: Drawable
    private val mPlayDrawable: Drawable
    private val mBackgroundImage: ImageView

    private var mCurrentArtUrl: String? = null
    private val mHandler = Handler()
    private var mMediaBrowser: MediaBrowserCompat? = null
    var supportMediaController: MediaControllerCompat? = null

    private val mConnectionCallback = object : MediaBrowserCompat.ConnectionCallback() {
        override fun onConnected() {
            try {
                connectToSession(mMediaBrowser?.sessionToken)
            } catch (e: RemoteException) {
            }
        }
    }

    init {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.custom_player, this)
        mBackgroundImage = findViewById(R.id.background_image) as ImageView
        mPauseDrawable = ContextCompat.getDrawable(getContext(), R.drawable.uamp_ic_pause_white_48dp)
        mPlayDrawable = ContextCompat.getDrawable(getContext(), R.drawable.uamp_ic_play_arrow_white_48dp)
        mPlayPause = findViewById(R.id.play_pause) as ImageView
        mSkipNext = findViewById(R.id.next) as ImageView
        mSkipPrev = findViewById(R.id.prev) as ImageView
        mStart = findViewById(R.id.startText) as TextView
        mEnd = findViewById(R.id.endText) as TextView
        mSeekbar = findViewById(R.id.seekBar1) as SeekBar
        mLine1 = findViewById(R.id.line1) as TextView
        mLine2 = findViewById(R.id.line2) as TextView
        mLine3 = findViewById(R.id.line3) as TextView
        mLoading = findViewById(R.id.progressBar1) as ProgressBar
        mControllers = findViewById(R.id.controllers)

        mSkipNext.setOnClickListener {
            val controls = supportMediaController?.transportControls
            controls?.skipToNext()
        }

        mSkipPrev.setOnClickListener { v ->
            val controls = supportMediaController?.transportControls
            controls?.skipToPrevious()
        }

        mPlayPause.setOnClickListener { v ->
            val state = supportMediaController?.playbackState
            if (state != null) {
                val controls = supportMediaController?.transportControls
                when (state.state) {
                    PlaybackStateCompat.STATE_PLAYING // fall through
                        , PlaybackStateCompat.STATE_BUFFERING -> {
                        controls?.pause()
                        stopSeekbarUpdate()
                    }
                    PlaybackStateCompat.STATE_PAUSED, PlaybackStateCompat.STATE_STOPPED -> {
                        controls?.play()
                        scheduleSeekbarUpdate()
                    }
                }
            }
        }

        mSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                mStart.text = DateUtils.formatElapsedTime((progress / 1000).toLong())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                stopSeekbarUpdate()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                supportMediaController?.transportControls?.seekTo(seekBar.progress.toLong())
                scheduleSeekbarUpdate()
            }
        })

        mMediaBrowser = MediaBrowserCompat(getContext(), ComponentName(getContext(), PlayerService::class.java), mConnectionCallback, null)
    }


    private val mUpdateProgressTask = { updateProgress() }

    private val mExecutorService = Executors.newSingleThreadScheduledExecutor()

    private var mScheduleFuture: ScheduledFuture<*>? = null
    private var mLastPlaybackState: PlaybackStateCompat? = null

    private val mCallback = object : MediaControllerCompat.Callback() {
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

    @Throws(RemoteException::class)
    private fun connectToSession(token: MediaSessionCompat.Token?) {
        val mediaController = MediaControllerCompat(context, token)
        if (mediaController.metadata == null) {
            return
        }
        supportMediaController = mediaController
        mediaController.registerCallback(mCallback)
        val state = mediaController.playbackState
        updatePlaybackState(state)
        val metadata = mediaController.metadata
        if (metadata != null) {
            updateMediaDescription(metadata.description)
            updateDuration(metadata)
        }
        updateProgress()
        if (state != null && (state.state == PlaybackStateCompat.STATE_PLAYING || state.state == PlaybackStateCompat.STATE_BUFFERING)) {
            scheduleSeekbarUpdate()
        }
    }

    private fun scheduleSeekbarUpdate() {
        stopSeekbarUpdate()
        if (!mExecutorService.isShutdown) {
            mScheduleFuture = mExecutorService.scheduleAtFixedRate(
                    { mHandler.post(mUpdateProgressTask) }, PROGRESS_UPDATE_INITIAL_INTERVAL,
                    PROGRESS_UPDATE_INTERNAL, TimeUnit.MILLISECONDS)
        }
    }

    private fun stopSeekbarUpdate() {
        if (mScheduleFuture != null) {
            mScheduleFuture?.cancel(false)
        }
    }

    fun onStart() {

        mMediaBrowser?.connect()
    }

    fun onStop() {
        mMediaBrowser?.disconnect()
        if (supportMediaController != null) {
            supportMediaController?.unregisterCallback(mCallback)
        }
    }

    fun onDestroy() {
        stopSeekbarUpdate()
        mExecutorService.shutdown()
    }

    private fun fetchImageAsync(description: MediaDescriptionCompat) {
        if (description.iconUri == null) {
            return
        }
        val artUrl = description.iconUri?.toString()
        mCurrentArtUrl = artUrl
        val cache = AlbumArtCache.getInstance()
        var art: Bitmap? = cache.getBigImage(artUrl)
        if (art == null) {
            art = description.iconBitmap
        }
        if (art != null) {
            // if we have the art cached or from the MediaDescription, use it:
            mBackgroundImage.setImageBitmap(art)
        } else {
            // otherwise, fetch a high res version and update:
            cache.fetch(artUrl) { artUrl, bitmap, icon ->
                // sanity check, in case a new fetch request has been done while
                // the previous hasn't yet returned:
                if (artUrl == mCurrentArtUrl) {
                    mBackgroundImage.setImageBitmap(bitmap)
                }
            }
        }
    }

    fun updateMediaDescription(description: MediaDescriptionCompat?) {
        if (description == null) {
            return
        }
        mLine1.text = description.title
        mLine2.text = description.subtitle
        fetchImageAsync(description)
    }

    private fun updateDuration(metadata: MediaMetadataCompat?) {
        if (metadata == null) {
            return
        }
        val duration = metadata.getLong(MediaMetadataCompat.METADATA_KEY_DURATION).toInt()
        mSeekbar.max = duration
        mEnd.text = DateUtils.formatElapsedTime((duration / 1000).toLong())
    }

    private fun updatePlaybackState(state: PlaybackStateCompat?) {
        if (state == null) {
            return
        }
        mLastPlaybackState = state
        if (supportMediaController != null && supportMediaController?.extras != null) {
            val castName: String? = supportMediaController?.extras?.getString(EXTRA_CONNECTED_CAST)
            val line3Text = if (castName == null)
                ""
            else
                resources
                        .getString(R.string.casting_to_device, castName)
            mLine3.text = line3Text
        }

        when (state.state) {
            PlaybackStateCompat.STATE_PLAYING -> {
                mLoading.visibility = View.INVISIBLE
                mPlayPause.visibility = View.VISIBLE
                mPlayPause.setImageDrawable(mPauseDrawable)
                mControllers.visibility = View.VISIBLE
                scheduleSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_PAUSED -> {
                mControllers.visibility = View.VISIBLE
                mLoading.visibility = View.INVISIBLE
                mPlayPause.visibility = View.VISIBLE
                mPlayPause.setImageDrawable(mPlayDrawable)
                stopSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_NONE, PlaybackStateCompat.STATE_STOPPED -> {
                mLoading.visibility = View.INVISIBLE
                mPlayPause.visibility = View.VISIBLE
                mPlayPause.setImageDrawable(mPlayDrawable)
                stopSeekbarUpdate()
            }
            PlaybackStateCompat.STATE_BUFFERING -> {
                mPlayPause.visibility = View.INVISIBLE
                mLoading.visibility = View.VISIBLE
                mLine3.setText(R.string.loading)
                stopSeekbarUpdate()
            }
        }

        mSkipNext.visibility = if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_NEXT == 0L)
            View.INVISIBLE
        else
            View.VISIBLE
        mSkipPrev.visibility = if (state.actions and PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS == 0L)
            View.INVISIBLE
        else
            View.VISIBLE
    }

    private fun updateProgress() {
        mLastPlaybackState?.let {
            var currentPosition = it.position
            if (it.state == PlaybackStateCompat.STATE_PLAYING) {
                val timeDelta = SystemClock.elapsedRealtime() - it.lastPositionUpdateTime
                currentPosition += (timeDelta.toInt() * it.playbackSpeed).toLong()
            }
            mSeekbar.progress = currentPosition.toInt()
        }
    }

    companion object {
        val EXTRA_CONNECTED_CAST = "com.example.android.uamp.CAST_NAME"
        private val PROGRESS_UPDATE_INTERNAL: Long = 1000
        private val PROGRESS_UPDATE_INITIAL_INTERVAL: Long = 100
    }
}
