package com.pietrantuono.podcasts.fullscreenplay.customcontrols

import android.os.SystemClock
import android.support.v4.media.session.PlaybackStateCompat

class PositionCalculator {
    fun calculatePostion(it: PlaybackStateCompat): Int {
        var currentPosition = it.position
        if (it.state == PlaybackStateCompat.STATE_PLAYING) {
            currentPosition += ((SystemClock.elapsedRealtime() - it.lastPositionUpdateTime).toInt() * it.playbackSpeed).toLong()
        }
        return currentPosition.toInt()
    }

}