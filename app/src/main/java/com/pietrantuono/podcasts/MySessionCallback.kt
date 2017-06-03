package com.pietrantuono.podcasts

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.ResultReceiver
import android.support.v4.media.RatingCompat
import android.support.v4.media.session.MediaSessionCompat

class MySessionCallback : MediaSessionCompat.Callback(){
    override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
        return super.onMediaButtonEvent(mediaButtonEvent)
    }

    override fun onRewind() {
        super.onRewind()
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
    }

    override fun onCustomAction(action: String?, extras: Bundle?) {
        super.onCustomAction(action, extras)
    }

    override fun onPrepare() {
        super.onPrepare()
    }

    override fun onFastForward() {
        super.onFastForward()
    }

    override fun onPlay() {
        super.onPlay()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSkipToQueueItem(id: Long) {
        super.onSkipToQueueItem(id)
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
    }

    override fun onPrepareFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPrepareFromMediaId(mediaId, extras)
    }

    override fun onCommand(command: String?, extras: Bundle?, cb: ResultReceiver?) {
        super.onCommand(command, extras, cb)
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onPrepareFromSearch(query: String?, extras: Bundle?) {
        super.onPrepareFromSearch(query, extras)
    }

    override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
    }

    override fun onPrepareFromUri(uri: Uri?, extras: Bundle?) {
        super.onPrepareFromUri(uri, extras)
    }

    override fun onPlayFromSearch(query: String?, extras: Bundle?) {
        super.onPlayFromSearch(query, extras)
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
    }

    override fun onSetRating(rating: RatingCompat?) {
        super.onSetRating(rating)
    }
}