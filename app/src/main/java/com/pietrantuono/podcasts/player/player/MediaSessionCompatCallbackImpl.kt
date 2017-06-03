package com.pietrantuono.podcasts.player.player

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat

class MediaSessionCompatCallbackImpl(context: Context) : MediaSessionCompat.Callback() {
    private val localPlayback: LocalPlayback

    init{
        localPlayback = LocalPlayback(context)
    }

    override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
        return false;
    }

    override fun onRewind() {

    }

    override fun onSeekTo(pos: Long) {

    }

    override fun onSkipToPrevious() {

    }

    override fun onCustomAction(action: String?, extras: Bundle?) {

    }

    override fun onPrepare() {

    }

    override fun onFastForward() {

    }

    override fun onPlay() {
        localPlayback.play(null)
    }

    override fun onStop() {

    }

    override fun onSkipToQueueItem(id: Long) {

    }

    override fun onSkipToNext() {

    }

    override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {

    }

    override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {

    }

    override fun onPause() {

    }

    override fun onPrepareFromSearch(query: String?, extras: Bundle?) {

    }

    override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {

    }

    override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {

    }

    override fun onPlayFromSearch(query: String?, extras: Bundle?) {

    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {

    }

    override fun onSetRating(rating: RatingCompat?) {

    }
}